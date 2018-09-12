package com.renatodias.brisachips.Fragmants.Cadastro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

public class CadastrarColaboradorFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        MenuLateralActivity.toolbar.setTitle("Cadastrar Colaborador");
        if (Constantes.isFragmentRegiao) {
            MenuLateralActivity.toolbar.setNavigationIcon(R.drawable.ic_menu_back);
            MenuLateralActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getFragmentManager().popBackStack();
                }
            });
        }

        return inflater.inflate(R.layout.fragment_cadastrar_colaborador, container, false);
    }


}
