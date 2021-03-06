package com.renatodias.brisachips.Fragmants.Cadastro;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.orhanobut.hawk.Hawk;
import com.renatodias.brisachips.Fragmants.Cadastro.Model.ImageId;
import com.renatodias.brisachips.Fragmants.Cadastro.Model.SalveCachePonto;
import com.renatodias.brisachips.Fragmants.Cidades.Model.City;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.Network.NetworkClinet;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;
import com.renatodias.brisachips.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarColaboradorFragment extends Fragment implements LocationListener{

    static ProgressDialog progressDialog;

    NetworkClinet service;

    Double latitude;
    Double longitude;

    String image;

    ArrayList<ImageId> imagesIds = new ArrayList<ImageId>();
    ArrayList<SalveCachePonto> cache = new ArrayList<SalveCachePonto>();
    ArrayList<String> base64 = new ArrayList<String>();

    static final int RIQUEST_LOCATION = 1;
    public static final int REQUEST_WRITE_STORAGE_REQUEST_CODE = 2;
    public static final int CAMERA_REQUEST = 1888;
    LocationManager locationManager;

    EditText nome_empresa;
    EditText nome_fantasia;
    EditText cpf_cnpj;
    EditText nome_incricao_estadual;
    EditText nome_incricao_municial;
    EditText nome_telefone_1;
    EditText nome_telefone_2;
    EditText nome_email;
    EditText nome_bairro;
    EditText nome_endereco;
    EditText amount;
    EditText prince;

    Switch card_machine;

    Spinner cidade;
    ImageButton camera;
    Button cadastrar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_cadastrar_colaborador, container, false);

        camera = (ImageButton) view.findViewById(R.id.image_cadastrar);
        cadastrar = (Button) view.findViewById(R.id.button_cadastro);
        card_machine = (Switch) view.findViewById(R.id.card_machine);

        cidade  = (Spinner) view.findViewById(R.id.cidade_cadastro);

        setTextEdits(view);
        setProgressLogin(getActivity());
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        setNavigation();
        getCitys();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCAmera(getActivity(),CAMERA_REQUEST);
            }

        });

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickCadastrar();
            }

        });

        return view;
    }

    public void cadastrarPontoColaborador(JSONObject points){
        progressDialog.show();

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),points.toString());
        final String pointsCadastrar = points.toString();

        if (Utils.verificaConexao(getActivity())) {
            service
                .getAPIWithKey()
                .cadastrarPontoColaborador(body)
                .enqueue(new Callback<ColaboradorSuper>() {
                    @Override
                    public void onResponse(Call<ColaboradorSuper> call, Response<ColaboradorSuper> response) {

                        ColaboradorSuper result = (ColaboradorSuper) response.body();
                        if (result != null) {
                            if (response.code() == 201 || response.code() == 200) {

                                createAlertViewSucesso("Sucesso!", "Ponto de venda adicionado com sucesso!", getActivity());
                                for(int i = 0; i < base64.size();i++){
                                    preparePostImage(result.getPoint_id(), base64.get(i));
                                }
                                getFragmentManager().popBackStack();

                            } else {
                                createAlertViewSucesso("Ops!", "Seu pedido falhou, tente novamente!", getActivity());
                            }
                        } else {

                            if (response.code() == 409 ) {
                                createAlertViewSucesso("Ops!", "E-mail já cadastrado!", getActivity());
                            } else {
                                createAlertViewSucesso("Ops!", "Seu pedido falhou, tente novamente!", getActivity());

                            }
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ColaboradorSuper> call, Throwable t) {

                        progressDialog.dismiss();
                        createAlertViewSucesso("Falhou!", "Verifique se está conectado a internet!", getActivity());
                        SalveCachePonto salve = new SalveCachePonto(pointsCadastrar, base64);
                        base64 = new ArrayList<>();
                        if(Hawk.contains("SalvePontsCache")) {
                            cache = Hawk.get("SalvePontsCache");
                            cache.add(salve);
                        } else {
                            cache.add(salve);
                        }
                        Hawk.put("SalvePontsCache", cache);
                        MenuLateralActivity.upload.setVisible(true);

                        try {
                            throw new InterruptedException("Erro na comunicação com o servidor!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        }else{
            createAlertViewSucesso("Falhou!", "Verifique se está conectado a internet!", getActivity());
            SalveCachePonto salve = new SalveCachePonto(pointsCadastrar, base64);
            base64 = new ArrayList<>();
            if(Hawk.contains("SalvePontsCache")) {
                cache = Hawk.get("SalvePontsCache");
                cache.add(salve);
            } else {
                cache.add(salve);
            }
            Hawk.put("SalvePontsCache", cache);
            MenuLateralActivity.upload.setVisible(true);
            progressDialog.dismiss();
        }
    }

    public void cadastrarPontoColaboradorCache(String points, final ArrayList<String> base64Cache, final Activity context){
        progressDialog.show();

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),points);
        final String pointsCadastrar = points;

        if (Utils.verificaConexao(context)) {
            service
                .getAPIWithKey()
                .cadastrarPontoColaborador(body)
                .enqueue(new Callback<ColaboradorSuper>() {
                    @Override
                    public void onResponse(Call<ColaboradorSuper> call, Response<ColaboradorSuper> response) {

                        ColaboradorSuper result = (ColaboradorSuper) response.body();
                        if (result != null) {
                            if (response.code() == 201 || response.code() == 200) {

                                createAlertViewSucesso("Sucesso!", "Ponto de venda adicionado com sucesso!", context);
                                for(int i = 0; i < base64Cache.size();i++){
                                    preparePostImage(result.getPoint_id(), base64Cache.get(i));
                                }

                            } else {
                                if (response.code() == 409 ) {
                                    createAlertViewSucesso("Ops!", "E-mail já cadastrado!", context);
                                } else {
                                    createAlertViewSucesso("Ops!", "Seu pedido falhou, tente novamente!", context);

                                }
                            }
                            MenuLateralActivity.upload.setVisible(false);
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ColaboradorSuper> call, Throwable t) {

                        createAlertViewSucesso("Falhou!", "Verifique se está conectado a internet!", context);
                        SalveCachePonto salve = new SalveCachePonto(pointsCadastrar, base64);
                        base64 = new ArrayList<>();
                        if(Hawk.contains("SalvePontsCache")) {
                            cache = Hawk.get("SalvePontsCache");
                            cache.add(salve);
                        } else {
                            cache.add(salve);
                        }
                        Hawk.put("SalvePontsCache", cache);
                        MenuLateralActivity.upload.setVisible(true);
                        progressDialog.dismiss();

                        try {
                            throw new InterruptedException("Erro na comunicação com o servidor!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        }else{
            createAlertViewSucesso("Falhou!", "Verifique se está conectado a internet!", context);
            SalveCachePonto salve = new SalveCachePonto(pointsCadastrar, base64);
            base64 = new ArrayList<>();
            if(Hawk.contains("SalvePontsCache")) {
                cache = Hawk.get("SalvePontsCache");
                cache.add(salve);
            } else {
                cache.add(salve);
            }
            Hawk.put("SalvePontsCache", cache);
            MenuLateralActivity.upload.setVisible(true);
            progressDialog.dismiss();
        }
    }

    public void postImage(String jsonObject){
        progressDialog.show();
        String jsonBarraN = jsonObject.replace("\\n", "");
        String json = jsonBarraN.replace("\\", "");

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),json);

        service
            .getAPIWithKey()
            .postImagem(body)
            .enqueue(new Callback<ImageId>() {
                @Override
                public void onResponse(Call<ImageId> call, Response<ImageId> response) {

                    ImageId result = (ImageId) response.body();
                    if(result != null) {
                        imagesIds.add(result);
//                        createAlertViewSucesso("Sucesso!", "Imagem enviada com sucesso!", getActivity());
                        progressDialog.dismiss();

                    }else {
//                        createAlertViewSucesso("Ops!", "Seu pedido falhou, tente novamente!", getActivity());
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ImageId> call, Throwable t) {

                    progressDialog.dismiss();
//                    createAlertViewSucesso("Falhou!","Verifique se está conectado a internet!", getActivity());
                    try {
                        throw  new InterruptedException("Erro na comunicação com o servidor!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void getCitys(){

        if (Utils.verificaConexao(getActivity())) {
            progressDialog.show();

            service
                .getAPIWithKey()
                .getAllCitys()
                .enqueue(new Callback<List<City>>() {
                    @Override
                    public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                        List<City> result = response.body();

                        if (result != null) {

                            Constantes.citys = result;
                            Hawk.put("TodasCidades",Constantes.citys);

                        } else {
                            Constantes.citys = Hawk.get("TodasCidades");
                        }

                        ArrayList<String> items = new ArrayList<String>();
                        items.add("Cidades");
                        for (int i = 1; i <= Constantes.citys.size(); i++) {
                            items.add(Constantes.citys.get(i - 1).getName().toString());
                        }

                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);

                        cidade.setAdapter(itemsAdapter);

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onFailure(Call<List<City>> call, Throwable t) {
                        Constantes.citys = Hawk.get("TodasCidades");

                        ArrayList<String> items = new ArrayList<String>();
                        items.add("Cidades");
                        for (int i = 1; i <= Constantes.citys.size(); i++) {
                            items.add(Constantes.citys.get(i - 1).getName().toString());
                        }

                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);

                        cidade.setAdapter(itemsAdapter);

                        progressDialog.dismiss();
                        try {
                            throw new InterruptedException("Erro na comunicação com o servidor!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        }else {
            Constantes.citys = Hawk.get("TodasCidades");

            ArrayList<String> items = new ArrayList<String>();
            items.add("Cidades");
            for (int i = 1; i <= Constantes.citys.size(); i++) {
                items.add(Constantes.citys.get(i - 1).getName().toString());
            }

            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);

            cidade.setAdapter(itemsAdapter);

            progressDialog.dismiss();
        }
    }

    public void getCAmera(Activity activity, int CODE) {

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.startActivityForResult(intent, CODE);

    }

    public void ClickCadastrar(){
        String cpf_cnpjString = CpfCnpjMaks.unmask(cpf_cnpj.getText().toString());

        if (nome_empresa.getText().toString() != "" &&
                cpf_cnpjString != "" &&
                nome_telefone_1.getText().toString() != "" &&
                nome_email.getText().toString() != "" &&
                nome_endereco.getText().toString() != "" &&
                nome_bairro.getText().toString() != "" &&
                conteudoPositionSpinner() > 0) {

            JSONObject user = new JSONObject();
            try {
                user.put("level", "3");
                user.put("fantasy_name", nome_fantasia.getText().toString());
                user.put("name", nome_empresa.getText().toString());
                user.put("cnpj", cpf_cnpjString);
                user.put("uf_number", nome_incricao_estadual.getText().toString());
                user.put("city_number", nome_incricao_municial.getText().toString());
                user.put("phone1", nome_telefone_1.getText().toString());
                user.put("phone2", nome_telefone_2.getText().toString());
                user.put("contact", nome_email.getText().toString());
                user.put("city", Constantes.citys.get(conteudoPositionSpinner()-1).getId());
                user.put("address", nome_endereco.getText().toString());
                user.put("neighborhood", nome_bairro.getText().toString());
                user.put("card_machine", card_machine.isChecked());

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JSONObject position = new JSONObject();
            try {
                position.put("latitude", latitude);
                position.put("longitude", longitude);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            JSONObject order = new JSONObject();
            try {
                order.put("amount", Integer.parseInt(amount.getText().toString()));
                order.put("price", Double.parseDouble(prince.getText().toString()));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JSONArray ids = idsFoto();

            JSONObject points = new JSONObject();
            try {
                points.put("user", user);
                points.put("position", position);
                points.put("images", ids);
                points.put("order", order);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            cadastrarPontoColaborador(points);

        } else {
            createAlertViewSucesso("Falhou!","Verifique se todos os campos estão preenchidos!", getActivity());
        }
    }


    public void setTextEdits(View view) {

        nome_empresa = (EditText) view.findViewById(R.id.nome_empresa);
//        setOnEditorActionListenerEditText(nome_empresa,nome_fantasia);
        nome_fantasia = (EditText) view.findViewById(R.id.nome_fantasia);
//        setOnEditorActionListenerEditText(nome_fantasia,cpf_cnpj);
        cpf_cnpj = (EditText) view.findViewById(R.id.nome_cpf_cnpj);
        cpf_cnpj.addTextChangedListener(CpfCnpjMaks.insert(cpf_cnpj));
//        setOnEditorActionListenerEditText(cpf_cnpj,nome_incricao_estadual);
        nome_incricao_estadual = (EditText) view.findViewById(R.id.nome_incricao_estadual);
        nome_incricao_municial = (EditText) view.findViewById(R.id.nome_incricao_municial);
//        setOnEditorActionListenerEditText(nome_incricao_estadual,nome_incricao_municial);
        nome_telefone_1 = (EditText) view.findViewById(R.id.nome_telefone_1);
        nome_telefone_1.addTextChangedListener(MaskEditUtil.mask(nome_telefone_1,MaskEditUtil.FORMAT_FONE));
//        setOnEditorActionListenerEditText(nome_incricao_municial,nome_telefone_1);
        nome_telefone_2 = (EditText) view.findViewById(R.id.nome_telefone_2);
        nome_telefone_2.addTextChangedListener(MaskEditUtil.mask(nome_telefone_2,MaskEditUtil.FORMAT_FONE));
//        setOnEditorActionListenerEditText(nome_telefone_1,nome_telefone_2);
        nome_email = (EditText) view.findViewById(R.id.email_cadastro);
//        setOnEditorActionListenerEditText(nome_telefone_2,nome_email);
        nome_bairro = (EditText) view.findViewById(R.id.bairro_cadastro);
//        setOnEditorActionListenerEditText(nome_bairro,nome_email);
        nome_endereco = (EditText) view.findViewById(R.id.endereco_cadastro);

        amount = (EditText) view.findViewById(R.id.qtd_chip);
        prince = (EditText) view.findViewById(R.id.valor_chips);


    }

    public void getLocation(){

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RIQUEST_LOCATION);
            getLocation();
        } else {

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getActivity(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String address = addresses.get(0).getAddressLine(0);
                    String[] address_separated = address.split("-");
                    nome_endereco.setText(address_separated[0]);
                    String [] separated = address_separated[1].split(",");
                    nome_bairro.setText(separated[0]);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RIQUEST_LOCATION:
                getLocation();
                break;
        }
    }

    private void setNavigation() {
        MenuLateralActivity.toolbar.setTitle("Cadastrar Colaborador");

        if (Hawk.contains("SalvePontsCache")) {
            MenuLateralActivity.upload.setVisible(true);
        }

        if (Constantes.isFragmentRegiao) {
            MenuLateralActivity.toolbar.setNavigationIcon(R.drawable.ic_menu_back);
            MenuLateralActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                }
            });
        }
    }

    public int conteudoPositionSpinner(){
        return cidade.getSelectedItemPosition();
    }

    public JSONArray idsFoto(){

        JSONArray ids = new JSONArray();

        for (int i = 0; i < imagesIds.size(); i++){
            ids.put(imagesIds.get(i).getId());
        }
        return ids;
    }

    private void setProgressLogin(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando...");
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public static void createAlertViewSucesso(String title, String subTitulo, Context context){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDialog = inflater.inflate(R.layout.dialog_home_pedidos, null);

        TextView titulo = viewDialog.findViewById(R.id.peca_chip_item);
        titulo.setText(title);

        TextView sub = viewDialog.findViewById(R.id.peca_chip_item_sub);
        sub.setText(subTitulo);

        TextView edit = viewDialog.findViewById(R.id.quantidade_item_alert);
        edit.setVisibility(View.INVISIBLE);

        Button pedir = (Button) viewDialog.findViewById(R.id.pedir_dialog_button);
        pedir.setText("Ok");

        Button cancelar = (Button) viewDialog.findViewById(R.id.cancelar_dialog_button);
        cancelar.setVisibility(View.INVISIBLE);

        mBuilder.setView(viewDialog);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data != null && requestCode == CAMERA_REQUEST) {

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);

            image = ConvertBitmapToString(resizedBitmap);

            base64.add(image);

            Upload();
        }

    }

    public void preparePostImage(int point_id, String imageBase64){

        JSONObject imageJson = new JSONObject();
        try {
            imageJson.put("point_id", point_id);
            imageJson.put("image", imageBase64);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        postImage(imageJson.toString());
    }

    public static String ConvertBitmapToString(Bitmap bitmap){
        String encodedImage = "";
        byte[] b = new byte[0];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        b = byteArrayOutputStream.toByteArray();
        encodedImage= Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        return encodedImage;
    }

    private void Upload() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                new UploadFile().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "your api link");
            } else {
                new UploadFile().execute("your api link");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    private class UploadFile extends AsyncTask<String, Void, Void> {


        private String Content;
        private String Error = null;
        String data = "";
        private BufferedReader reader;


        protected void onPreExecute() {

            try {

                data += "&" + URLEncoder.encode("image", "UTF-8") + "=" + "data:image/png;base64," + image;

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        protected Void doInBackground(String... urls) {

            HttpURLConnection connection = null;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("POST");
                con.setUseCaches(false);
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setRequestProperty("Content-Length", "" + data.getBytes().length);
                con.setRequestProperty("Connection", "Keep-Alive");
                con.setDoOutput(true);

                OutputStream os = con.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                //make request
                writer.write(data);
                writer.flush();
                writer.close();
                reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                Content = sb.toString();
            } catch (Exception ex) {
                Error = ex.getMessage();
            }
            return null;

        }


        protected void onPostExecute(Void unused) {

            try {

                if (Content != null) {
                    JSONObject jsonResponse = new JSONObject(Content);
                    String status = jsonResponse.getString("status");
                    if ("200".equals(status)) {

                        Toast.makeText(getActivity().getApplicationContext(), "File uploaded successfully", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(getActivity().getApplicationContext(), "Something is wrong ! Please try again.", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void updatePontos(Activity context){
        if (Utils.verificaConexao(context)) {
            if (Hawk.contains("SalvePontsCache")) {
                ArrayList<SalveCachePonto> jsonArray = Hawk.get("SalvePontsCache");
                Hawk.delete("SalvePontsCache");
                cache = new ArrayList<>();
                base64 = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    cadastrarPontoColaboradorCache(jsonArray.get(i).getArrayJSONObject(), jsonArray.get(i).getBase64(), context);
                }
            }
        } else {
            createAlertViewSucesso("Falhou!", "Verifique se está conectado a internet!", context);
        }
    }
}
