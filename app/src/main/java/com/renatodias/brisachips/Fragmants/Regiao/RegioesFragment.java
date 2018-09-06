package com.renatodias.brisachips.Fragmants.Regiao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renatodias.brisachips.Fragmants.Home.Adapter.HomeAdapter;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.Fragmants.Regiao.Model.Regioes;
import com.renatodias.brisachips.Fragmants.Regiao.adapter.RegiaoAdapter;
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
//        final FragmentActivity context = getActivity();

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
//                                createFloatingActionButton();

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

    public void createRecyclerView(){

        final FragmentActivity context = getActivity();

//        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_home);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(layoutManager);

        final RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_regioes);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RegiaoAdapter(Constantes.regioes);
        recyclerView.setAdapter(adapter);

//        if (Utils.isSuper(level)) {
//            adapter = new RegiaoAdapter(Constantes.regioes);
//        } else {
//            adapter = new HomeAdapter(getListOrdes(Constantes.colaborador));
//        }

        recyclerView.setAdapter(adapter);
    }


    private void setProgressLogin(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando...");
    }
}
