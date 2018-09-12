package com.renatodias.brisachips.Fragmants.Cadastro;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

public class CadastrarColaboradorFragment extends Fragment {

    EditText cpf_cnpj;
    EditText nome_telefone_1;
    EditText nome_telefone_2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_cadastrar_colaborador, container, false);

        setTextEdits(view);

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

        return view;
    }


    public void setTextEdits(View view) {
        cpf_cnpj = (EditText) view.findViewById(R.id.nome_cpf_cnpj);
        cpf_cnpj.addTextChangedListener(CpfCnpjMaks.insert(cpf_cnpj));

        nome_telefone_1 = (EditText) view.findViewById(R.id.nome_telefone_1);
        nome_telefone_1.addTextChangedListener(MaskEditUtil.mask(nome_telefone_1,MaskEditUtil.FORMAT_FONE));

        nome_telefone_2 = (EditText) view.findViewById(R.id.nome_telefone_2);
        nome_telefone_2.addTextChangedListener(MaskEditUtil.mask(nome_telefone_2,MaskEditUtil.FORMAT_FONE));

    }

}
