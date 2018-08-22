package com.renatodias.brisachips.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.renatodias.brisachips.Menu.MenuLateralActivity;
import com.renatodias.brisachips.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void clickLoginEntrar(View view) {
        Intent mainIntent = new Intent(LoginActivity.this,MenuLateralActivity.class);
        LoginActivity.this.startActivity(mainIntent);
        LoginActivity.this.finish();
    }
}
