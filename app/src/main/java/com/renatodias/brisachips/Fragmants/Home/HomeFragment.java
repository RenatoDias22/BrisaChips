package com.renatodias.brisachips.Fragmants.Home;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.renatodias.brisachips.Fragmants.Home.Adapter.ContentItem;
import com.renatodias.brisachips.Fragmants.Home.Adapter.Header;
import com.renatodias.brisachips.Fragmants.Home.Adapter.HomeAdapter;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.Network.NetworkClinet;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;
import com.renatodias.brisachips.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    Toolbar toolbar;
    HomeAdapter adapter;

    NetworkClinet service;

    View contextView;

    ProgressDialog progressDialog;

    String level = ""+ Constantes.user.getUser_level();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contextView = inflater.inflate(R.layout.fragment_home, container, false);

        MenuLateralActivity.toolbar.setTitle("Home");

        Constantes.isFragmentRegiao = false;

        setProgressLogin(getActivity());

//        if(Constantes.id_pontos_colaborador != ""){
//            getCitysOrdesColaborador(false);
//            createFloatingActionButton();
//        } else {

            if (Utils.isSuper(level)) {
                getCitysOrdesSuper(false, null);
            } else {
                getCitysOrdes(false);
                createFloatingActionButton();
            }
//        }

        return contextView;

    }


    public void getCitysOrdesSuper(final boolean atualization, HomeAdapter adapterAux){

        progressDialog.show();
        final HomeAdapter adapterAux2 = adapterAux;

         service
             .getAPIWithKey()
             .getAllPartnersOrders()
             .enqueue(new Callback<List<ColaboradorSuper>>() {
                @Override
                public void onResponse(Call<List<ColaboradorSuper>> call, Response<List<ColaboradorSuper>> response) {

                    List<ColaboradorSuper> result = (List<ColaboradorSuper>) response.body();

                    if(result != null) {

                        Constantes.colaboradorSuper = result;
                        if (!atualization)
                            createRecyclerView();
                        else
                            loadRecycleViewCitys(adapterAux2);
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<List<ColaboradorSuper>> call, Throwable t) {
                    progressDialog.dismiss();
                    try {
                        throw  new InterruptedException("Erro na comunicação com o servidor!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        });
    }


//    public void getCitysOrdesColaborador(final boolean atualization){
//
//        progressDialog.show();
//
////        int id = Integer.parseInt(Constantes.id_pontos_colaborador);
//
//        HashMap<String, Integer> jsonParams = new HashMap<>();
////        jsonParams.put("id", id);
//
//        service
//            .getAPIWithKey()
//            .getOrdesColaborador(jsonParams)
//            .enqueue(new Callback<ColaboradorSuper>() {
//                @Override
//                public void onResponse(Call<ColaboradorSuper> call, Response<ColaboradorSuper> response) {
//
//                    ColaboradorSuper result = (ColaboradorSuper) response.body();
//
//                    if(result != null) {
//
//                        Constantes.colaborador = result;
//                        if (!atualization)
//                            createRecyclerView();
//                        else
//                            loadRecycleViewCitysColaborador();
//
//                        progressDialog.dismiss();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ColaboradorSuper> call, Throwable t) {
//                    progressDialog.dismiss();
//                    try {
//                        throw  new InterruptedException("Erro na comunicação com o servidor!");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//    }


    public void getCitysOrdes(final boolean atualization){

        progressDialog.show();

        service
            .getAPIWithKey()
            .getAllOrders()
            .enqueue(new Callback<ColaboradorSuper>() {
                @Override
                public void onResponse(Call<ColaboradorSuper> call, Response<ColaboradorSuper> response) {

                    ColaboradorSuper result = (ColaboradorSuper) response.body();

                    if(result != null) {

                        Constantes.colaborador = result;
                        if (!atualization)
                            createRecyclerView();
                        else
                            loadRecycleViewCitys(null);

                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ColaboradorSuper> call, Throwable t) {
                    progressDialog.dismiss();
                    try {
                        throw  new InterruptedException("Erro na comunicação com o servidor!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void postAskForOrdes(int orderCount, Context context){
        setProgressLogin(context);
        progressDialog.show();

        HashMap<String, Integer> jsonParams = new HashMap<>();
        jsonParams.put("amount", orderCount);

        final Context c = context;

        service
            .getAPIWithKey()
            .askForOrdes(jsonParams)
            .enqueue(new Callback<ColaboradorSuper>() {
                @Override
                public void onResponse(Call<ColaboradorSuper> call, Response<ColaboradorSuper> response) {

                    ColaboradorSuper result = (ColaboradorSuper) response.body();
                    if (result.getMessage() != "") {
                        getCitysOrdes(true);
//                        if(Constantes.id_pontos_colaborador != "") {
//                            getCitysOrdesColaborador(true);
//                        }else{
//                            getCitysOrdes(true);
//                        }
                        createAlertViewSucesso("Sucesso!", result.getMessage(), c);
                        progressDialog.dismiss();
                    }else{
                        createAlertViewSucesso("Ops!", "Seu pedido falhou, tente novamente!", c);
                    }
                }

                @Override
                public void onFailure(Call<ColaboradorSuper> call, Throwable t) {

                    progressDialog.dismiss();
                    createAlertViewSucesso("Falhou!","Verifique se está conectado a internet!", c);
                    try {
                        throw  new InterruptedException("Erro na comunicação com o servidor!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        });
    }

    public void postAskForAcceptOrder(int id, Context context, HomeAdapter adapteraux2){
        setProgressLogin(context);
        progressDialog.show();
        final Context c = context;
        final HomeAdapter adapteraux = adapteraux2;

        service
            .getAPIWithKey()
            .askForAcceptOrder(id)
            .enqueue(new Callback<ColaboradorSuper>() {
                @Override
                public void onResponse(Call<ColaboradorSuper> call, Response<ColaboradorSuper> response) {

                    ColaboradorSuper result = (ColaboradorSuper) response.body();

                    if (result.getMessage() != "") {
                        getCitysOrdesSuper(true, adapteraux);
                        createAlertViewSucesso("Sucesso!", result.getMessage(), c);
                        progressDialog.dismiss();
                    }else{
                        createAlertViewSucesso("Ops!", "Seu atendimento falhou, tente novamente!", c);
                    }
                }

                @Override
                public void onFailure(Call<ColaboradorSuper> call, Throwable t) {

                    progressDialog.dismiss();
                    createAlertViewSucesso("Falhou!","Verifique se está conectado a internet!", c);
                    try {
                        throw  new InterruptedException("Erro na comunicação com o servidor!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    public void loadRecycleViewCitys(HomeAdapter adapterAux2){

        if (Utils.isSuper(level)) {
            adapterAux2.setItems(getListCitysOrdes(Constantes.colaboradorSuper), getActivity());
            adapterAux2.setAdapter(adapterAux2);
            adapterAux2.notifyDataSetChanged();
        } else {
            adapter.setItems(getListOrdes(Constantes.colaborador), getActivity());
            adapter.notifyDataSetChanged();
        }
    }

//    public void loadRecycleViewCitysColaborador(){
//
//        adapter.setItems(getListOrdes(Constantes.colaborador), getActivity());
//        adapter.notifyDataSetChanged();
//    }

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
            adapter = new HomeAdapter(getListCitysOrdes(Constantes.colaboradorSuper), this.getActivity());
            adapter.setAdapter(adapter);
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
                createAlertViewPedido(view.getContext());
            }
        });
    }

    public void createAlertViewPedido(Context context){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDialog = inflater.inflate(R.layout.dialog_home_pedidos, null);

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
                postAskForOrdes(orderCount, v.getContext());
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

    public void createAlertViewAtender(String title, String subTitulo, int id, Context context, HomeAdapter adapteraux){

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
        pedir.setText("Atender");

        Button cancelar = (Button) viewDialog.findViewById(R.id.cancelar_dialog_button);
        cancelar.setText("Recusar");

        final int idClick = id;
        final HomeAdapter adapteraux2 = adapteraux;

        mBuilder.setView(viewDialog);
        final AlertDialog dialog = mBuilder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postAskForAcceptOrder(idClick, v.getContext(), adapteraux2);
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

    private void setProgressLogin(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Carregando...");
    }

}
