package com.renatodias.brisachips.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.renatodias.brisachips.Login.Model.AuthUser;
import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.Network.NetworkClinet;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public EditText emailLogin;
    public EditText senhaLogin;

    ProgressDialog progressDialog;

    NetworkClinet service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setProgressLogin();

        emailLogin = (EditText) findViewById(R.id.emailLogin);
        senhaLogin = (EditText) findViewById(R.id.senhaLogin);

        emailLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    String cap = emailLogin.getText().toString().replace('\n', ' ')
                            .trim();
                    emailLogin.setText(cap);
                    senhaLogin.requestFocus();
                }
                return false;
            }

        });

        senhaLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    login();
                    return true;
                }
                return false;
            }
        });
    }

    private void setProgressLogin() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
    }

    public void clickLoginEntrar(View view) {
        login();
    }

    public void login(){
        progressDialog.show();
        String email = emailLogin.getText().toString().trim();
        String senha = senhaLogin.getText().toString().trim();

        service
            .getNetworkClinet()
            .login(email, senha)
            .enqueue(new Callback<AuthUser>() {

            @Override
            public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {
                progressDialog.dismiss();
//                generateDataList(response.body());
                AuthUser result = response.body();

                if(result != null) {
                    Constantes.user = result.getUser();
                    Constantes.token = result.getAuth_token();

                    Intent mainIntent = new Intent(LoginActivity.this, MenuLateralActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();
                }else{
                    createAlertViewSucesso("Algo deu errado :(", "Verifique seu dados e tente novamente!");
                }
            }

            @Override
            public void onFailure(Call<AuthUser> call, Throwable t) {
                progressDialog.dismiss();
                createAlertViewSucesso("Algo deu errado :(", "Erro na comunicação com o servidor!");
                try {
                    throw  new InterruptedException("Erro na comunicação com o servidor!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void createAlertViewSucesso(String title, String subTitulo){

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewDialog = inflater.inflate(R.layout.dialog_home_pedidos, null);

        TextView titulo = viewDialog.findViewById(R.id.peca_chip_item);
        titulo.setText(title);

        TextView sub = viewDialog.findViewById(R.id.peca_chip_item_sub);
        sub.setText(subTitulo);

        TextView edit = viewDialog.findViewById(R.id.quantidade_item_alert);
        edit.setVisibility(View.INVISIBLE);

        Button pedir = (Button) viewDialog.findViewById(R.id.pedir_dialog_button);
        pedir.setText("Ok");

        Button cancelar = (Button) viewDialog.findViewById(R.id.cancelar_dialog_button);
        cancelar.setVisibility(View.INVISIBLE);

        mBuilder.setView(viewDialog);
        final AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        pedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

//    "auth":"http://chips.brisanet.net.br/api/auth/","cities":"http://chips.brisanet.net.br/api/cities/","regions":"http://chips.brisanet.net.br/api/regions/","points":"http://chips.brisanet.net.br/api/points/","images":"http://chips.brisanet.net.br/api/images/","orders":"http://chips.brisanet.net.br/api/orders/","ships":"http://chips.brisanet.net.br/api/ships/

}
