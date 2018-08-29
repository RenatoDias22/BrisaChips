package com.renatodias.brisachips.Fragmants.Home.Adapter;

import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;

public class ContentItem extends ColaboradorSuper {

    private long id;
    private String date;
    private int status;
    private int amount;
    private int user;
    private int point;
    private String rollnumber;


    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }

    public int getAmount() {
        return amount;
    }

    public int getUser() {
        return user;
    }

    public int getPoint() {
        return point;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setRollnumber(String rollnumber) {
        this.rollnumber = rollnumber;
    }
}
