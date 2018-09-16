package com.renatodias.brisachips.Fragmants.Cadastro;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.renatodias.brisachips.Fragmants.Cidades.Model.City;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.Network.NetworkClinet;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastrarColaboradorFragment extends Fragment implements LocationListener{

    ProgressDialog progressDialog;

    NetworkClinet service;

    Double latitude;
    Double longetude;
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

        conteudoPositionSpinner();
        conteudoFoto();
    }


    public void setTextEdits(View view) {

        nome_empresa = (EditText) view.findViewById(R.id.nome_empresa);
        nome_fantasia = (EditText) view.findViewById(R.id.nome_fantasia);

        cpf_cnpj = (EditText) view.findViewById(R.id.nome_cpf_cnpj);
        cpf_cnpj.addTextChangedListener(CpfCnpjMaks.insert(cpf_cnpj));

        nome_incricao_estadual = (EditText) view.findViewById(R.id.nome_incricao_estadual);
        nome_incricao_municial = (EditText) view.findViewById(R.id.nome_incricao_municial);

        nome_telefone_1 = (EditText) view.findViewById(R.id.nome_telefone_1);
        nome_telefone_1.addTextChangedListener(MaskEditUtil.mask(nome_telefone_1,MaskEditUtil.FORMAT_FONE));

        nome_telefone_2 = (EditText) view.findViewById(R.id.nome_telefone_2);
        nome_telefone_2.addTextChangedListener(MaskEditUtil.mask(nome_telefone_2,MaskEditUtil.FORMAT_FONE));

        nome_email = (EditText) view.findViewById(R.id.email_cadastro);
        nome_bairro = (EditText) view.findViewById(R.id.bairro_cadastro);
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
                longetude = location.getLongitude();
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

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longetude = location.getLongitude();
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

    public void conteudoPositionSpinner(){
        int posicao = cidade.getSelectedItemPosition();
        if(posicao == 0){

        }else {
            String itemSelecionado = Constantes.citys.get(posicao).getName();
        }
    }

    public void conteudoFoto(){
        String filePath = file.getPath();
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//        mImageView.setImageBitmap(bitmap);
    }

    private void setProgressLogin(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando...");
    }
}
