package com.renatodias.brisachips.Fragmants.Cidades;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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

import com.renatodias.brisachips.Fragmants.Cidades.adapter.CidadesAdapter;
import com.renatodias.brisachips.Fragmants.Home.HomeFragment;
import com.renatodias.brisachips.Fragmants.Regiao.adapter.RegiaoAdapter;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.R;

import java.util.ArrayList;

public class CidadesFragment extends Fragment {

    RecyclerView.Adapter adapter;
//    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_cidades, container, false);
        final FragmentActivity context = getActivity();
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_cidades);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CidadesAdapter();
        recyclerView.setAdapter(adapter);

//        toolbar = (Toolbar) view.findViewById(R.id.toolbar_cidades);
        MenuLateralActivity.toolbar.setTitle("Cidades");
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);




//        final ActionBar actionBar =  ((AppCompatActivity)getActivity()).getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled( true );
//        }

        return view;
    }

}
