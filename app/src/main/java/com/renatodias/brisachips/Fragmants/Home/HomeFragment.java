package com.renatodias.brisachips.Fragmants.Home;

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

import com.renatodias.brisachips.Fragmants.Home.Adapter.ContentItem;
import com.renatodias.brisachips.Fragmants.Home.Adapter.Header;
import com.renatodias.brisachips.Fragmants.Home.Adapter.HomeAdapter;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.Network.NetworkClinet;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    Toolbar toolbar;
    RecyclerView.Adapter adapter;

    NetworkClinet service;

    View contextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        contextView = inflater.inflate(R.layout.fragment_home, container, false);

        MenuLateralActivity.toolbar.setTitle("Home");
        getColaboradoresSuper();

        return contextView;

    }


    public void getColaboradoresSuper(){

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

                        createFloatingActionButton();

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


    private List<ColaboradorSuper> getList(List<ColaboradorSuper> arrayList) {

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

    public void createRecyclerView(){

        final FragmentActivity context = getActivity();
        final RecyclerView recyclerView = (RecyclerView) contextView.findViewById(R.id.recycler_view_home);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeAdapter(getList(Constantes.colaboradorSuper));
        recyclerView.setAdapter(adapter);
    }

    public void createFloatingActionButton(){

        FloatingActionButton fab = contextView.findViewById(R.id.fab_home);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View viewDialog = getLayoutInflater().inflate(R.layout.dialog_home_pedidos, null);

                mBuilder.setView(viewDialog);
                AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }
}
