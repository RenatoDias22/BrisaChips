package com.renatodias.brisachips.Fragmants.Regiao.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Regioes {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("user")
    private int user;


    public Regioes() {
    }

    public Regioes(long id, String name, int user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setUser(int user) {
        this.user = user;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public int getUser() {
        return user;
    }

}
