package com.renatodias.brisachips.Login;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

    NetworkClinet service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        emailLogin = (EditText) findViewById(R.id.emailLogin);
        senhaLogin = (EditText) findViewById(R.id.senhaLogin);

        emailLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String cap = emailLogin.getText().toString().replace('\n', ' ')
                            .trim();
                    emailLogin.setText(cap);
                    senhaLogin.requestFocus();//match this behavior to your 'Send' (or Confirm) button
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

    public void clickLoginEntrar(View view) {
        login();
    }

    public void login(){

        String email = emailLogin.getText().toString().trim();
        String senha = senhaLogin.getText().toString().trim();

        service
                .getNetworkClinet()
                .login(email, senha)
                .enqueue(new Callback<AuthUser>() {

            @Override
            public void onResponse(Call<AuthUser> call, Response<AuthUser> response) {
//                progressDoalog.dismiss();
//                generateDataList(response.body());
                AuthUser result = response.body();

                if(result != null) {
                    Constantes.user = result.getUser();
                    Constantes.token = result.getAuth_token();

                    Intent mainIntent = new Intent(LoginActivity.this, MenuLateralActivity.class);
                    LoginActivity.this.startActivity(mainIntent);
                    LoginActivity.this.finish();
                }
            }

            @Override
            public void onFailure(Call<AuthUser> call, Throwable t) {
//                progressDoalog.dismiss();
                try {
                    throw  new InterruptedException("Erro na comunicação com o servidor!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    "auth":"http://chips.brisanet.net.br/api/auth/","cities":"http://chips.brisanet.net.br/api/cities/","regions":"http://chips.brisanet.net.br/api/regions/","points":"http://chips.brisanet.net.br/api/points/","images":"http://chips.brisanet.net.br/api/images/","orders":"http://chips.brisanet.net.br/api/orders/","ships":"http://chips.brisanet.net.br/api/ships/

    //user_level
    //super : 0
    //Lider regional parceiros :1
    //lider regional interno :2
    //parceiro
    //vendendor interno
}
