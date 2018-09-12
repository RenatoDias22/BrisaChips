package com.renatodias.brisachips.Fragmants.Regiao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renatodias.brisachips.Fragmants.Cadastro.CadastrarColaboradorFragment;
import com.renatodias.brisachips.Fragmants.Cidades.CidadesFragment;
import com.renatodias.brisachips.Fragmants.Home.Adapter.HomeAdapter;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.Fragmants.Regiao.Model.Regioes;
import com.renatodias.brisachips.Fragmants.Regiao.adapter.RegiaoAdapter;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.Network.NetworkClinet;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;
import com.renatodias.brisachips.Utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegioesFragment extends Fragment {

    RecyclerView.Adapter adapter;

    ProgressDialog progressDialog;

    NetworkClinet service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_regioes, container, false);

        setToolbar();

        createFloatingActionButton(view);

        Constantes.isFragmentRegiao = true;

        setProgressLogin(getActivity());
        getRegioes();

        return view;
    }

    public void getRegioes(){
        {

            progressDialog.show();

            service
                .getAPIWithKey()
                .getAllRegions()
                .enqueue(new Callback<List<Regioes>>() {
                    @Override
                    public void onResponse(Call<List<Regioes>> call, Response<List<Regioes>> response) {

                        List<Regioes> result = response.body();

                        if(result != null) {

                            Constantes.regioes = result;
                            createRecyclerView();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Regioes>> call, Throwable t) {
                        progressDialog.dismiss();
                        try {
                            throw  new InterruptedException("Erro na comunicação com o servidor!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        }


    }

    private void createFloatingActionButton(View view) {

        FloatingActionButton fab = view.findViewById(R.id.fab_regiao);
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

        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_regioes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RegiaoAdapter(Constantes.regioes);
        recyclerView.setAdapter(adapter);

    }

    public void setToolbar(){

        MenuLateralActivity.toolbar.setTitle("Região");
        MenuLateralActivity.toolbar.setNavigationIcon(R.drawable.ic_menu_24dp);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), MenuLateralActivity.drawer, MenuLateralActivity.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        MenuLateralActivity.drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setProgressLogin(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando...");
    }
}
