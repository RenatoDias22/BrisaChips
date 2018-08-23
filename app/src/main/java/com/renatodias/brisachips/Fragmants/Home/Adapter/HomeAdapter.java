package com.renatodias.brisachips.Fragmants.Home.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renatodias.brisachips.Fragmants.Colaboradores.Adapter.ColaboradorAdapter;
import com.renatodias.brisachips.Fragmants.Colaboradores.Model.Colaborador;
import com.renatodias.brisachips.R;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolderHome>{
    List<Colaborador> colaboradores = new ArrayList<Colaborador>();

    class ViewHolderHome extends RecyclerView.ViewHolder{

//        public TextView titulo;
//        public TextView quantidade;
//        public TextView estoque;

        public ViewHolderHome(View itemView) {
            super(itemView);
//            titulo = (TextView) itemView.findViewById(R.id.item_name_home);
//            estoque = (TextView) itemView.findViewById(R.id.item_estoque_home);
//            quantidade = (TextView) itemView.findViewById(R.id.item_qtd_home);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                }
            });
        }
    }

    public HomeAdapter.ViewHolderHome onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_home, viewGroup, false);
        HomeAdapter.ViewHolderHome viewHolderColaborador = new HomeAdapter.ViewHolderHome(view);

        return viewHolderColaborador;
    }

    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolderHome holder, int position) {

//        holder.titulo.setText(colaboradores.get(position).getNome());
//        holder.estoque.setText(colaboradores.get(position).getEstoque());
//        holder.quantidade.setText(colaboradores.get(position).getQuantidade());

    }

    @Override
    public int getItemCount() {
        colaboradores.clear();
        colaboradores.add(new Colaborador(1, "João", 0, "Estoque", "16"));
        colaboradores.add(new Colaborador(1, "Zé", 0, "Estoque", "13"));
        colaboradores.add(new Colaborador(1, "Joaqui", 0, "Estoque", "6"));
        colaboradores.add(new Colaborador(1, "Marcos", 0, "Estoque", "14"));
        colaboradores.add(new Colaborador(1, "Neto", 0, "Estoque", "25"));
        colaboradores.add(new Colaborador(1, "Roberto", 0, "Estoque", "20"));
        colaboradores.add(new Colaborador(1, "Romario", 0, "Estoque", "56"));
        colaboradores.add(new Colaborador(1, "Joaci", 0, "Estoque", "0"));
        colaboradores.add(new Colaborador(1, "Maranhão", 0, "Estoque", "3"));
        colaboradores.add(new Colaborador(1, "Humbira", 0, "Estoque", "98"));
        colaboradores.add(new Colaborador(1, "Carlos", 0, "Estoque", "20"));
        colaboradores.add(new Colaborador(1, "Jordão", 0, "Estoque", "10"));
        colaboradores.add(new Colaborador(1, "Caicão", 0, "Estoque", "15"));
        colaboradores.add(new Colaborador(1, "Josué", 0, "Estoque", "90"));

        return colaboradores.size();
    }
}