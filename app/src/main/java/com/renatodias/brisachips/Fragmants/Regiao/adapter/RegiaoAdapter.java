package com.renatodias.brisachips.Fragmants.Regiao.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renatodias.brisachips.Fragmants.Cidades.CidadesFragment;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.Fragmants.Regiao.Model.Regioes;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

public class RegiaoAdapter extends RecyclerView.Adapter<RegiaoAdapter.ViewHolderRegioes>{

    List<Regioes> list;
    String level = ""+ Constantes.user.getUser_level();

    public RegiaoAdapter(List<Regioes> headerItems) {
        this.list = headerItems;
    }

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

        holder.titulo.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
