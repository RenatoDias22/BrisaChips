package com.renatodias.brisachips.Fragmants.Home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.renatodias.brisachips.Fragmants.Home.Adapter.ContentItem;
import com.renatodias.brisachips.Fragmants.Home.Adapter.Header;
import com.renatodias.brisachips.Fragmants.Home.Adapter.HomeAdapter;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.Login.LoginActivity;
import com.renatodias.brisachips.Login.Model.AuthUser;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.Network.NetworkClinet;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;
import com.renatodias.brisachips.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    Toolbar toolbar;
    RecyclerView.Adapter adapter;

    NetworkClinet service;

    View contextView;

    String level = ""+ Constantes.user.getUser_level();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contextView = inflater.inflate(R.layout.fragment_home, container, false);

        MenuLateralActivity.toolbar.setTitle("Home");

        if (Utils.isSuper(level)) {
            getCitysOrdesSuper();
        } else {
            getCitysOrdes();
        }

        return contextView;

    }


    public void getCitysOrdesSuper(){

         service
             .getAPIWithKey()
             .getAllPartnersOrders()
             .enqueue(new Callback<List<ColaboradorSuper>>() {
                @Override
                public void onResponse(Call<List<ColaboradorSuper>> call, Response<List<ColaboradorSuper>> response) {
//                progressDoalog.dismiss();
//                generateDataList(response.body());
                List<ColaboradorSuper> result = (List<ColaboradorSuper>) response.body();

                if(result != null) {

                    Constantes.colaboradorSuper = result;

                    createRecyclerView();

                }
            }

            @Override
            public void onFailure(Call<List<ColaboradorSuper>> call, Throwable t) {
//                progressDoalog.dismiss();
                try {
                    throw  new InterruptedException("Erro na comunicação com o servidor!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getCitysOrdes(){

        service
            .getAPIWithKey()
            .getAllOrders()
            .enqueue(new Callback<ColaboradorSuper>() {
                @Override
                public void onResponse(Call<ColaboradorSuper> call, Response<ColaboradorSuper> response) {
//                progressDoalog.dismiss();
//                generateDataList(response.body());
                    ColaboradorSuper result = (ColaboradorSuper) response.body();

                    if(result != null) {

                        Constantes.colaborador = result;
                        createRecyclerView();
                        createFloatingActionButton();

                    }
                }

                @Override
                public void onFailure(Call<ColaboradorSuper> call, Throwable t) {
                    //                progressDoalog.dismiss();
                    try {
                        throw  new InterruptedException("Erro na comunicação com o servidor!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void postAskForOrdes(int orderCount){

        HashMap<String, Integer> jsonParams = new HashMap<>();
        jsonParams.put("amount", orderCount);

        service
            .getAPIWithKey()
            .askForOrdes(jsonParams)
            .enqueue(new Callback<ColaboradorSuper>() {
                @Override
                public void onResponse(Call<ColaboradorSuper> call, Response<ColaboradorSuper> response) {
    //                progressDoalog.dismiss();
    //                generateDataList(response.body());
                    ColaboradorSuper result = (ColaboradorSuper) response.body();
                    createAlertViewSucesso("Sucesso!","Seu pedido foi realizado com sucesso!");
                }

                @Override
                public void onFailure(Call<ColaboradorSuper> call, Throwable t) {
                    //                progressDoalog.dismiss();
                    createAlertViewSucesso("Falhou!","Verifique se está conectado a internet!");
                    try {
                        throw  new InterruptedException("Erro na comunicação com o servidor!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        });
    }


    private List<ColaboradorSuper> getListCitysOrdes(List<ColaboradorSuper> arrayList) {

        List<ColaboradorSuper> list = new ArrayList<>();
        for(int j=0; j < arrayList.size();j++) {

            Header header = new Header();
            header.setHeader(arrayList.get(j).getCity());
            list.add(header);

            for (int i = 0; i < arrayList.get(j).getOrders().size(); i++) {
                ContentItem item = new ContentItem();
                item.setId(arrayList.get(j).getOrders().get(i).getId());
                item.setDate(arrayList.get(j).getOrders().get(i).getDate());
                item.setStatus(arrayList.get(j).getOrders().get(i).getStatus());
                item.setAmount(arrayList.get(j).getOrders().get(i).getAmount());
                item.setUser(arrayList.get(j).getOrders().get(i).getUser());
                item.setPoint(arrayList.get(j).getOrders().get(i).getPoint());
                item.setRollnumber(i + "");
                list.add(item);
            }
        }
        return list;
    }

    private List<ColaboradorSuper> getListOrdes(ColaboradorSuper arrayList) {

        List<ColaboradorSuper> list = new ArrayList<>();

        for (int i = 0; i < arrayList.getOrders().size(); i++) {
            ContentItem item = new ContentItem();
            item.setId(arrayList.getOrders().get(i).getId());
            item.setDate(arrayList.getOrders().get(i).getDate());
            item.setStatus(arrayList.getOrders().get(i).getStatus());
            item.setAmount(arrayList.getOrders().get(i).getAmount());
            item.setUser(arrayList.getOrders().get(i).getUser());
            item.setPoint(arrayList.getOrders().get(i).getPoint());
            item.setRollnumber(i + "");
            list.add(item);
        }

        return list;
    }

    public void createRecyclerView(){

        final FragmentActivity context = getActivity();
        final RecyclerView recyclerView = (RecyclerView) contextView.findViewById(R.id.recycler_view_home);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        if (Utils.isSuper(level)) {
            adapter = new HomeAdapter(getListCitysOrdes(Constantes.colaboradorSuper));
        } else {
            adapter = new HomeAdapter(getListOrdes(Constantes.colaborador));
        }

        recyclerView.setAdapter(adapter);
    }

    public void createFloatingActionButton(){

        FloatingActionButton fab = contextView.findViewById(R.id.fab_home);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertViewPedido();
            }
        });
    }

    public void createAlertViewPedido(){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View viewDialog = getLayoutInflater().inflate(R.layout.dialog_home_pedidos, null);

        final TextView qtdPedido = (TextView) viewDialog.findViewById(R.id.quantidade_item_alert);
        Button pedir = (Button) viewDialog.findViewById(R.id.pedir_dialog_button);
        Button cancelar = (Button) viewDialog.findViewById(R.id.cancelar_dialog_button);

        mBuilder.setView(viewDialog);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orderCount = Integer.parseInt(qtdPedido.getText().toString());
                postAskForOrdes(orderCount);
                dialog.dismiss();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void createAlertViewSucesso(String title, String subTitulo){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View viewDialog = getLayoutInflater().inflate(R.layout.dialog_home_pedidos, null);

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

    public void createAlertViewAtender(String title, String subTitulo){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View viewDialog = getLayoutInflater().inflate(R.layout.dialog_home_pedidos, null);

        TextView titulo = viewDialog.findViewById(R.id.peca_chip_item);
        titulo.setText(title);

        TextView sub = viewDialog.findViewById(R.id.peca_chip_item_sub);
        sub.setText(subTitulo);

        TextView edit = viewDialog.findViewById(R.id.quantidade_item_alert);
        edit.setVisibility(View.INVISIBLE);

        Button pedir = (Button) viewDialog.findViewById(R.id.pedir_dialog_button);
        pedir.setText("Atender");

        Button cancelar = (Button) viewDialog.findViewById(R.id.cancelar_dialog_button);
        cancelar.setText("Recusar");

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

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
