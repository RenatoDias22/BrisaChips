package com.renatodias.brisachips.Fragmants.Cadastro;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.renatodias.brisachips.Fragmants.Cadastro.Model.LatlongPosition;
import com.renatodias.brisachips.Fragmants.Cadastro.Model.User;
import com.renatodias.brisachips.Fragmants.Cidades.Model.City;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.Network.NetworkClinet;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarColaboradorFragment extends Fragment implements LocationListener{

    ProgressDialog progressDialog;

    NetworkClinet service;

    Double latitude;
    Double longitude;

    Uri outputFileUri;
    File file;

    static final int RIQUEST_LOCATION = 1;
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

    public void cadastrarPontoColaborador(JSONObject jsonObject){
        progressDialog.show();

        service
            .getAPIWithKey()
            .cadastrarPontoColaborador(jsonObject)
            .enqueue(new Callback<ColaboradorSuper>() {
                @Override
                public void onResponse(Call<ColaboradorSuper> call, Response<ColaboradorSuper> response) {

                    ColaboradorSuper result = (ColaboradorSuper) response.body();
                    if (result.getMessage() != "") {

                        createAlertViewSucesso("Sucesso!", result.getMessage(), getActivity());
                        progressDialog.dismiss();
                    }else{
                        createAlertViewSucesso("Ops!", "Seu pedido falhou, tente novamente!", getActivity());
                    }
                }

                @Override
                public void onFailure(Call<ColaboradorSuper> call, Throwable t) {

                    progressDialog.dismiss();
                    createAlertViewSucesso("Falhou!","Verifique se está conectado a internet!", getActivity());
                    try {
                        throw  new InterruptedException("Erro na comunicação com o servidor!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void getCitys(){

        progressDialog.show();

        service
            .getAPIWithKey()
            .getAllCitys()
            .enqueue(new Callback<List<City>>() {
                @Override
                public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                    List<City> result = response.body();

                    if(result != null) {

                        Constantes.citys = result;

                        ArrayList<String> items = new ArrayList<String>();
                        items.add("Cidades");
                        for (int i = 1 ; i<= Constantes.citys.size(); i++){
                            items.add(Constantes.citys.get(i-1).getName().toString());
                        }

                        ArrayAdapter<String> itemsAdapter =
                                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);

//                        ArrayAdapter adapter = new ArrayAdapter(getContext(),
//                                android.R.layout.simple_spinner_item, Constantes.citys);
                        cidade.setAdapter(itemsAdapter);

                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<City>> call, Throwable t) {
                    progressDialog.dismiss();
                    try {
                        throw  new InterruptedException("Erro na comunicação com o servidor!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void getCAmera(Activity activity, int CODE) {

        String arquivoFoto = activity.getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        file = new File(arquivoFoto);
        outputFileUri = Uri.fromFile(file);

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(camera, CODE);

    }

    public void ClickCadastrar(){


        if (nome_empresa.getText().toString() != "" &&
                cpf_cnpj.getText().toString() != "" &&
                nome_telefone_1.getText().toString() != "" &&
                nome_email.getText().toString() != "" &&
                nome_endereco.getText().toString() != "" &&
                nome_bairro.getText().toString() != "" &&
                conteudoPositionSpinner() > 0) {

            JSONObject user = new JSONObject();
            try {
                user.put("fantasy_name", nome_fantasia.getText().toString());
                user.put("name", nome_empresa.getText().toString());
                user.put("cnpj", cpf_cnpj.getText().toString());
                user.put("uf_number", nome_incricao_estadual.getText().toString());
                user.put("city_number", nome_incricao_municial.getText().toString());
                user.put("phone1", nome_telefone_1.getText().toString());
                user.put("phone2", nome_telefone_2.getText().toString());
                user.put("contact", nome_email.getText().toString());
                user.put("city", Constantes.citys.get(conteudoPositionSpinner()-1).getId());
                user.put("address", nome_endereco.getText().toString());
                user.put("neighborhood", nome_bairro.getText().toString());

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

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("user", user);
                jsonObject.put("position", position);
                jsonObject.put("image", conteudoFoto());

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            cadastrarPontoColaborador(jsonObject);
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

    }

    public void getLocation(){

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RIQUEST_LOCATION);
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
//        if(posicao == 0){
//
//        }else {
//            String itemSelecionado = Constantes.citys.get(posicao).getName();
//        }
    }

    public ImageView conteudoFoto(){
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        ImageView mImageView = new ImageButton(getActivity());
        mImageView.setImageBitmap(bitmap);
        return mImageView;
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

    public void createAlertViewSucesso(String title, String subTitulo, Context context){

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
}
