package com.renatodias.brisachips.Fragmants.Cadastro.Model;

import com.google.gson.annotations.SerializedName;

public class ImageId {

    @SerializedName("id")
    private long id;

    public ImageId(){}

    public ImageId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
