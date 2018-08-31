package com.renatodias.brisachips.Fragmants.Home.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.renatodias.brisachips.Fragmants.Home.HomeFragment;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;
import com.renatodias.brisachips.Utils.Utils;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    View contextView;

    Context context;
    List<ColaboradorSuper> list;
    String level = ""+ Constantes.user.getUser_level();

    public HomeAdapter(List<ColaboradorSuper> headerItems, Context context) {
        this.list = headerItems;
        context = context;
    }

    public HomeAdapter(List<ColaboradorSuper> headerItems) {
        this.list = headerItems;
    }



    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        if(Utils.isSuper(level)) {

            if (i == TYPE_HEADER) {

                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.header_layout, viewGroup, false);
                HomeAdapter.ViewHolderHomeHeader viewHolderHeaderColaborador = new HomeAdapter.ViewHolderHomeHeader(view);

                return viewHolderHeaderColaborador;

            } else {

                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.card_layout_home, viewGroup, false);
                HomeAdapter.ViewHolderHome viewHolderColaborador = new HomeAdapter.ViewHolderHome(view);

                return viewHolderColaborador;
            }

        }else{

            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.card_layout_home, viewGroup, false);
            HomeAdapter.ViewHolderHome viewHolderColaborador = new HomeAdapter.ViewHolderHome(view);

            return viewHolderColaborador;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderHomeHeader) {

            Header  currentItem = (Header) list.get(position);
            ViewHolderHomeHeader VHeader = (ViewHolderHomeHeader) holder;
            VHeader.txtTitle.setText(currentItem.getHeader());

        } else
            if(holder instanceof ViewHolderHome) {

                ViewHolderHome VHome = (ViewHolderHome) holder;

                ContentItem currentItem = (ContentItem) list.get(position);

                String pedido = "" + currentItem.getId();
                String dateStr = "" + currentItem.getDate();
                String[] separated = dateStr.split("T");
                String[] separated2 = separated[0].split("-");
                String data = "" + separated2[0] + "/" + separated2[1] + "/" + separated2[2];
                String qtd = "" + currentItem.getAmount();
                String status = "" + currentItem.getStatus();
                VHome.pedido.setText(pedido);
                VHome.data.setText(data);
                VHome.quantidade.setText(qtd);

                switch (status) {
                    case "0":
                        VHome.status.setText("DESPACHE ");
                        break;
                    case "1":
                        VHome.status.setText("PENDENTE ");
                        break;
                    case "2":
                        VHome.status.setText("ATENDIDO");
                        break;
                    case "3":
                        VHome.status.setText("RECEBIDO ");
                        break;
                    case "4":
                        VHome.status.setText("CANCELADO");
                        break;
                     default:
                         VHome.status.setText("CANCELADO");
                }

        }
    }

    public int getItemViewType(int position) {
        if(Utils.isSuper(level)) {
            if (isPositionHeader(position))
                return TYPE_HEADER;
            return TYPE_ITEM;
        }else{
            return TYPE_ITEM;
        }

    }

    private boolean isPositionHeader(int position) {

        return list.get(position) instanceof Header;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderHomeHeader extends RecyclerView.ViewHolder {

        TextView txtTitle;

        public ViewHolderHomeHeader(View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.txtHeader);
        }
    }

    class ViewHolderHome extends RecyclerView.ViewHolder{

        public final TextView pedido;
        public final TextView data;
        public final TextView quantidade;
        public final TextView status;


        public ViewHolderHome(View itemView) {
            super(itemView);

            pedido = (TextView) itemView.findViewById(R.id.item_pedido);
            data = (TextView) itemView.findViewById(R.id.item_data);
            quantidade = (TextView) itemView.findViewById(R.id.item_qtd);
            status = (TextView) itemView.findViewById(R.id.item_status);
            final Context contextaux = context;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    if(Utils.isSuper(level)) {
                        if (status.getText().toString().equals("PENDENTE ") || status.getText().toString().equals("ATENDIDO")) {
                            int id = Integer.parseInt(pedido.getText().toString());
                            HomeFragment f = new HomeFragment();
                            f.createAlertViewAtender("Atender Pedido", "Deseja atender a esté pedido?", id, v.getContext());

                        }
                    }
                }
            });
        }
    }
}