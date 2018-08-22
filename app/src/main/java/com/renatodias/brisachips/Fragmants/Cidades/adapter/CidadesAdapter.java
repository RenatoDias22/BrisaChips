package com.renatodias.brisachips.Fragmants.Cidades.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.renatodias.brisachips.Fragmants.Cidades.CidadesFragment;
import com.renatodias.brisachips.Fragmants.Colaboradores.ColaboradoresFragment;
import com.renatodias.brisachips.Fragmants.Regiao.adapter.RegiaoAdapter;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.R;

import java.util.ArrayList;
import java.util.List;

public class CidadesAdapter extends RecyclerView.Adapter<CidadesAdapter.ViewHolderCidades>{
    List<String> cidades = new ArrayList<String>();

    class ViewHolderCidades extends RecyclerView.ViewHolder{

        public TextView titulo;


        public ViewHolderCidades(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.item_title_cidades);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    MenuLateralActivity activity = (MenuLateralActivity) v.getContext();
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

        holder.titulo.setText(cidades.get(position));

    }

    @Override
    public int getItemCount() {
        cidades.clear();
        cidades.add("Pereiro");
        cidades.add("Jaguaribe");
        cidades.add("Fortaleza");
        cidades.add("Limoeiro do Norte");
        cidades.add("Morada Nova");
        cidades.add("Juazeiro do Norte");
        cidades.add("São Miguel");
        cidades.add("Pau dos Ferros");
        cidades.add("Beberibe");
        cidades.add("Russas");

        return cidades.size();
    }
}
