package com.renatodias.brisachips.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.ConnectivityManager;

public class Utils {

    public static boolean isSuper(String value){
        if (value.equals("0") || value.equals("1") || value.equals("2")) {
            return true;
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public static boolean verificaConexao(Activity activity) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }
}
