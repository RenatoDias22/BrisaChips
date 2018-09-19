package com.renatodias.brisachips.Fragmants.Cadastro.Model;

import com.google.gson.annotations.SerializedName;

public class ImageId {

    @SerializedName("id")
    private int id;

    @SerializedName("image")
    private String image;

    public ImageId(){}

    public ImageId(int id, String image) {
        this.id = id;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String url) {
        this.image = url;
    }
}
