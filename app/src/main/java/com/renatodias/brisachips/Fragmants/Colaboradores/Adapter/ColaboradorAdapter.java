package com.renatodias.brisachips.Fragmants.Colaboradores.Adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renatodias.brisachips.Fragmants.Cidades.Model.City;
import com.renatodias.brisachips.Fragmants.Colaboradores.ColaboradoresFragment;
import com.renatodias.brisachips.Fragmants.Colaboradores.Model.Ponts;
import com.renatodias.brisachips.Fragmants.Home.HomeFragment;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

public class ColaboradorAdapter extends RecyclerView.Adapter<ColaboradorAdapter.ViewHolderColaborador>{

    List<Ponts> list;

    public ColaboradorAdapter(List<Ponts> headerItems) {
        this.list = headerItems;
    }

    class ViewHolderColaborador extends RecyclerView.ViewHolder{

        public final TextView titulo;
        public long id;
//        public TextView quantidade;
//        public TextView estoque;

        public ViewHolderColaborador(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.item_name_colaborador);

//            estoque = (TextView) itemView.findViewById(R.id.item_estoque_colaborador);
//            quantidade = (TextView) itemView.findViewById(R.id.item_qtd_colaborador);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
//                    MenuLateralActivity activity = (MenuLateralActivity) v.getContext();
//                    Constantes.id_pontos_colaborador = "" + id;
//                    Fragment homeFragment = new HomeFragment();
//                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, homeFragment).addToBackStack(null).commit();
                }
            });
        }
    }

    public ColaboradorAdapter.ViewHolderColaborador onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_colaborador, viewGroup, false);
        ColaboradorAdapter.ViewHolderColaborador viewHolderColaborador = new ColaboradorAdapter.ViewHolderColaborador(view);

        return viewHolderColaborador;
    }

    @Override
    public void onBindViewHolder(ColaboradorAdapter.ViewHolderColaborador holder, int position) {

        holder.titulo.setText(list.get(position).getName());
        holder.id = list.get(position).getId();
//        holder.estoque.setText(colaboradores.get(position).getEstoque());
//        holder.quantidade.setText(colaboradores.get(position).getQuantidade());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }
}