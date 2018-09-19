package com.renatodias.brisachips.Fragmants.Cidades.Model;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;


    public City() {
    }

    public City(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
