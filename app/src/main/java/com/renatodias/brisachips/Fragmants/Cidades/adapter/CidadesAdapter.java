package com.renatodias.brisachips.Fragmants.Cidades.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renatodias.brisachips.Fragmants.Cidades.CidadesFragment;
import com.renatodias.brisachips.Fragmants.Cidades.Model.City;
import com.renatodias.brisachips.Fragmants.Colaboradores.ColaboradoresFragment;
import com.renatodias.brisachips.Fragmants.Regiao.Model.Regioes;
import com.renatodias.brisachips.Fragmants.Regiao.adapter.RegiaoAdapter;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

public class CidadesAdapter extends RecyclerView.Adapter<CidadesAdapter.ViewHolderCidades>{

    List<City> list;

    public CidadesAdapter(List<City> headerItems) {
        this.list = headerItems;
    }

    class ViewHolderCidades extends RecyclerView.ViewHolder{

        public TextView titulo;
        public long id;


        public ViewHolderCidades(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.item_title_cidades);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    MenuLateralActivity activity = (MenuLateralActivity) v.getContext();
                    Constantes.url_id_pontos_colaborador = "" + id;
                    Fragment colaboradorFragment = new ColaboradoresFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, colaboradorFragment).addToBackStack(null).commit();
                }
            });
        }
    }

    public CidadesAdapter.ViewHolderCidades onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_cidades, viewGroup, false);
        CidadesAdapter.ViewHolderCidades viewHolderCidades = new CidadesAdapter.ViewHolderCidades(view);

        return viewHolderCidades;
    }

    @Override
    public void onBindViewHolder(CidadesAdapter.ViewHolderCidades holder, int position) {

        holder.titulo.setText(list.get(position).getName());
        holder.id = list.get(position).getId();

    }

    @Override
    public int getItemCount() {


        return list.size();
    }
}
