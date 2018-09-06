package com.renatodias.brisachips.Fragmants.Cidades.Model;

import com.google.gson.annotations.SerializedName;

public class City {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;


    public City() {
    }

    public City(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
