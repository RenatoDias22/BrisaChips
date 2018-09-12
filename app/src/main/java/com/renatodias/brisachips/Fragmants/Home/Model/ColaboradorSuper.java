package com.renatodias.brisachips.Fragmants.Home.Model;

import com.google.gson.annotations.SerializedName;
import com.renatodias.brisachips.Login.Model.AuthUser;

import java.util.Date;
import java.util.List;

public class ColaboradorSuper {

    @SerializedName("city")
    private String city;
    @SerializedName("orders")
    private List<Orders> orders;

    @SerializedName("message")
    private String message;


    public String getCity() {
        return city;
    }

    public String getMessage() {
        return message;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public static class Orders{
        @SerializedName("id")
        private long id;
        @SerializedName("date")
        private String date;
        @SerializedName("status")
        private int status;
        @SerializedName("amount")
        private int amount;
        @SerializedName("user")
        private int user;
        @SerializedName("point")
        private int point;

        public Orders() {
        }

        public Orders(long id, String date, int status, int amount, int user, int point) {
            this.id = id;
            this.date = date;
            this.status = status;
            this.amount = amount;
            this.user = user;
            this.point = point;
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
    }
}
