package com.renatodias.brisachips.Menu;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.renatodias.brisachips.Fragmants.Home.HomeFragment;
import com.renatodias.brisachips.Fragmants.Regiao.RegioesFragment;
import com.renatodias.brisachips.Login.LoginActivity;
import com.renatodias.brisachips.R;
import com.renatodias.brisachips.Utils.Constantes;
import com.renatodias.brisachips.Utils.Utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MenuLateralActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Toolbar toolbar;
    public static DrawerLayout drawer;
    String level = ""+ Constantes.user.getUser_level();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lateral);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_activity_menu_lateral_home));
        setSupportActionBar(toolbar);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setFragmentManager();

        setFloatActionButton();

        setDrawerMenu();

        setnavigationViewMenu();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        FragmentManager fragmantManager = getSupportFragmentManager();
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragmantManager.beginTransaction().replace(R.id.contenedor, new HomeFragment()).commit();
            toolbar.setTitle(getString(R.string.title_activity_menu_lateral_home));
        } else if (id == R.id.nav_list) {
            fragmantManager.beginTransaction().replace(R.id.contenedor, new RegioesFragment()).commit();
            toolbar.setTitle(getString(R.string.title_activity_menu_lateral_regiao));
        } else if (id == R.id.nav_map) {
            fragmantManager.beginTransaction().replace(R.id.contenedor, new RegioesFragment()).commit();
            toolbar.setTitle(getString(R.string.title_activity_menu_lateral_regiao));
        } else if (id == R.id.nav_back) {
            Intent mainIntent = new Intent(MenuLateralActivity.this,LoginActivity.class);
            MenuLateralActivity.this.startActivity(mainIntent);
            MenuLateralActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setnavigationViewMenu(){

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView2.getHeaderView(0);

        TextView navUsername = (TextView) headerView.findViewById(R.id.header_menu_name);
        navUsername.setText(Constantes.user.getName());

        TextView navUserEmail = (TextView) headerView.findViewById(R.id.header_menu_subetexto);
        navUserEmail.setText(Constantes.user.getEmail());

        if (!Utils.isSuper(level)) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_list).setVisible(false);
            nav_Menu.findItem(R.id.nav_map).setVisible(false);
        }

    }

    private void setDrawerMenu(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    private void setFloatActionButton(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setFragmentManager(){
        FragmentManager fragmantManager = getSupportFragmentManager();
        fragmantManager.beginTransaction().replace(R.id.contenedor, new HomeFragment()).commit();

    }
}
