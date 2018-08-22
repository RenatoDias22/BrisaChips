package com.renatodias.brisachips.Fragmants.Regiao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renatodias.brisachips.Fragmants.Cidades.CidadesFragment;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.R;

import java.util.ArrayList;
import java.util.List;

public class RegiaoAdapter extends RecyclerView.Adapter<RegiaoAdapter.ViewHolderRegioes>{
    List<String> regioes = new ArrayList<String>();

    class ViewHolderRegioes extends RecyclerView.ViewHolder{

        public TextView titulo;


        public ViewHolderRegioes(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.item_title_regioes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    MenuLateralActivity activity = (MenuLateralActivity) v.getContext();
                    Fragment cidadesFragment = new CidadesFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, cidadesFragment).addToBackStack(null).commit();
                }
            });
        }
    }

    public ViewHolderRegioes onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout_regioes, viewGroup, false);
        ViewHolderRegioes viewHolderRegioes = new ViewHolderRegioes(view);

        return viewHolderRegioes;
    }

    @Override
    public void onBindViewHolder(ViewHolderRegioes holder, int position) {

        holder.titulo.setText(regioes.get(position));

    }

    @Override
    public int getItemCount() {
        regioes.clear();
        regioes.add("Cariri");
        regioes.add("Centro Sul");
        regioes.add("Grande Fortaleza");
        regioes.add("Litoral Leste");
        regioes.add("Litoral Norte");
        regioes.add("Litoral Oeste / Vale do Curu");
        regioes.add("Maciço de Baturite");
        regioes.add("Serra da Ibiapaba");
        regioes.add("Sertão Central");
        regioes.add("Sertão de Canidé");
        regioes.add("Sertão Crateús");
        regioes.add("Sertão Dos Inhamuns");
        regioes.add("Sertão de Sobral");
        regioes.add("Vale Jaguaribe");

        return regioes.size();
    }
}
