package com.renatodias.brisachips.Fragmants.Cidades;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renatodias.brisachips.Fragmants.Cadastro.CadastrarColaboradorFragment;
import com.renatodias.brisachips.Fragmants.Cidades.Model.City;
import com.renatodias.brisachips.Fragmants.Cidades.adapter.CidadesAdapter;
import com.renatodias.brisachips.Fragmants.Home.HomeFragment;
import com.renatodias.brisachips.Fragmants.Regiao.Model.Regioes;
import com.renatodias.brisachips.Fragmants.Regiao.adapter.RegiaoAdapter;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.Network.NetworkClinet;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CidadesFragment extends Fragment {

    RecyclerView.Adapter adapter;

    ProgressDialog progressDialog;

    NetworkClinet service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_cidades, container, false);

        final FragmentActivity context = getActivity();

        setToolbar();

        Constantes.isFragmentRegiao = false;

        createFloatingActionButton(view);

        setProgressLogin(getActivity());
        getCitys();

        return view;
    }

    public void getCitys(){

        progressDialog.show();

        service
            .getAPIWithKey()
            .getAllCitys(Constantes.url_id_cidade)
            .enqueue(new Callback<List<City>>() {
                @Override
                public void onResponse(Call<List<City>> call, Response<List<City>> response) {

                    List<City> result = response.body();

                    if(result != null) {

                        Constantes.citys = result;
                        createRecyclerView();

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

    public void createRecyclerView(){

        final FragmentActivity context = getActivity();

        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_cidades);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CidadesAdapter(Constantes.citys);
        recyclerView.setAdapter(adapter);

    }

    public void createFloatingActionButton(View view){

        FloatingActionButton fab = view.findViewById(R.id.fab_cidade);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuLateralActivity activity = (MenuLateralActivity) view.getContext();
                Fragment cadastrarColaboradorFragment = new CadastrarColaboradorFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, cadastrarColaboradorFragment).addToBackStack(null).commit();

            }
        });
    }

    public void setToolbar(){

        MenuLateralActivity.toolbar.setTitle("Cidades");
        MenuLateralActivity.toolbar.setNavigationIcon(R.drawable.ic_menu_back);
        MenuLateralActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
    }

    private void setProgressLogin(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando...");
    }

}
