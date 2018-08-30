package com.renatodias.brisachips.Fragmants.Colaboradores.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renatodias.brisachips.R;

import java.util.ArrayList;
import java.util.List;

public class ColaboradorAdapter extends RecyclerView.Adapter<ColaboradorAdapter.ViewHolderColaborador>{
//    List<Colaborador> colaboradores = new ArrayList<Colaborador>();

    class ViewHolderColaborador extends RecyclerView.ViewHolder{

//        public TextView titulo;
//        public TextView quantidade;
//        public TextView estoque;

        public ViewHolderColaborador(View itemView) {
            super(itemView);
//            titulo = (TextView) itemView.findViewById(R.id.item_name_colaborador);
//            estoque = (TextView) itemView.findViewById(R.id.item_estoque_colaborador);
//            quantidade = (TextView) itemView.findViewById(R.id.item_qtd_colaborador);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

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

//        holder.titulo.setText(colaboradores.get(position).getNome());
//        holder.estoque.setText(colaboradores.get(position).getEstoque());
//        holder.quantidade.setText(colaboradores.get(position).getQuantidade());

    }

    @Override
    public int getItemCount() {
//        colaboradores.clear();
//        colaboradores.add(new Colaborador(1, "João", 0, "Estoque", "16"));
//        colaboradores.add(new Colaborador(1, "Zé", 0, "Estoque", "13"));
//        colaboradores.add(new Colaborador(1, "Joaqui", 0, "Estoque", "6"));
//        colaboradores.add(new Colaborador(1, "Marcos", 0, "Estoque", "14"));
//        colaboradores.add(new Colaborador(1, "Neto", 0, "Estoque", "25"));
//        colaboradores.add(new Colaborador(1, "Roberto", 0, "Estoque", "20"));
//        colaboradores.add(new Colaborador(1, "Romario", 0, "Estoque", "56"));
//        colaboradores.add(new Colaborador(1, "Joaci", 0, "Estoque", "0"));
//        colaboradores.add(new Colaborador(1, "Maranhão", 0, "Estoque", "3"));
//        colaboradores.add(new Colaborador(1, "Humbira", 0, "Estoque", "98"));
//        colaboradores.add(new Colaborador(1, "Carlos", 0, "Estoque", "20"));
//        colaboradores.add(new Colaborador(1, "Jordão", 0, "Estoque", "10"));
//        colaboradores.add(new Colaborador(1, "Caicão", 0, "Estoque", "15"));
//        colaboradores.add(new Colaborador(1, "Josué", 0, "Estoque", "90"));

        return 10;//colaboradores.size();
    }
}