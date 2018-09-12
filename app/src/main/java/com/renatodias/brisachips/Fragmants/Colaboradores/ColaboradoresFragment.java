package com.renatodias.brisachips.Fragmants.Colaboradores;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.renatodias.brisachips.Fragmants.Cadastro.CadastrarColaboradorFragment;
import com.renatodias.brisachips.Fragmants.Cidades.CidadesFragment;
import com.renatodias.brisachips.Fragmants.Cidades.Model.City;
import com.renatodias.brisachips.Fragmants.Cidades.adapter.CidadesAdapter;
import com.renatodias.brisachips.Fragmants.Colaboradores.Adapter.ColaboradorAdapter;
import com.renatodias.brisachips.Fragmants.Colaboradores.Model.Ponts;
import com.renatodias.brisachips.Fragmants.Home.HomeFragment;
import com.renatodias.brisachips.Fragmants.Regiao.adapter.RegiaoAdapter;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.Network.NetworkClinet;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ColaboradoresFragment extends Fragment {

    RecyclerView.Adapter adapter;

    ProgressDialog progressDialog;

    NetworkClinet service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_colaboradores, container, false);

        setToolbar();
        Constantes.isFragmentRegiao = false;

        setProgressLogin(getActivity());
        getColaboradores();

        return view;
    }

    public void getColaboradores(){

        progressDialog.show();

        service
            .getAPIWithKey()
            .getAllPonts(Constantes.url_id_pontos_colaborador)
            .enqueue(new Callback<List<Ponts>>() {
                @Override
                public void onResponse(Call<List<Ponts>> call, Response<List<Ponts>> response) {

                    List<Ponts> result = response.body();

                    if(result != null) {

                        Constantes.ponts = result;
                        createRecyclerView();
                        createFloatingActionButton();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<Ponts>> call, Throwable t) {
                    progressDialog.dismiss();
                    try {
                        throw  new InterruptedException("Erro na comunicação com o servidor!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    private void createFloatingActionButton() {

        FloatingActionButton fab = getActivity().findViewById(R.id.fab_colaborador);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuLateralActivity activity = (MenuLateralActivity) view.getContext();
                Fragment cadastrarColaboradorFragment = new CadastrarColaboradorFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, cadastrarColaboradorFragment).addToBackStack(null).commit();

            }
        });
    }

    public void createRecyclerView(){

        final FragmentActivity context = getActivity();
        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_colaborador);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ColaboradorAdapter(Constantes.ponts);
        recyclerView.setAdapter(adapter);

    }

    public void setToolbar(){
        MenuLateralActivity.toolbar.setTitle("Ponto de Venda");
    }


    private void setProgressLogin(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando...");
    }

}
