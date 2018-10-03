package com.renatodias.brisachips.Fragmants.Cadastro.Model;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class SalveCachePonto {
    String arrayJSONObject = "";
    ArrayList<String> base64 = new ArrayList<String>();

    public SalveCachePonto(){};

    public SalveCachePonto(String arrayJSONObject, ArrayList<String> base64) {
        this.arrayJSONObject = arrayJSONObject;
        this.base64 = base64;
    }

    public String getArrayJSONObject() {
        return arrayJSONObject;
    }

    public void setArrayJSONObject(String arrayJSONObject) {
        this.arrayJSONObject = arrayJSONObject;
    }

    public ArrayList<String> getBase64() {
        return base64;
    }

    public void setBase46(ArrayList<String> base64) {
        this.base64 = base64;
    }
}
