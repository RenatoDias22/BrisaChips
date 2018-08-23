package com.renatodias.brisachips.Login.Model;

import com.google.gson.annotations.SerializedName;

public class AuthUser {
    @SerializedName("auth_token")
    private String auth_token;
    @SerializedName("user")
    private User user;

    public AuthUser(String auth_token, User user) {
        this.auth_token = auth_token;
        this.user = user;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public User getUser() {
        return user;
    }

    public class User{
        @SerializedName("id")
        private long id;
        @SerializedName("name")
        private String name;
        @SerializedName("email")
        private String email;


        public User() {
        }

        public User(long id, String name, String email, String password, String newPassword, String accountType, String authTokenType, String accountName, String token) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }

}
