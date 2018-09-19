package com.renatodias.brisachips.Fragmants.Colaboradores.Model;

import com.google.gson.annotations.SerializedName;

public class Ponts {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;

    public Ponts(long id, String name) {
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
