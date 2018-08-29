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
        @SerializedName("has_password")
        private Boolean has_password;
        @SerializedName("profile_photo")
        private Boolean profile_photo;
        @SerializedName("facebook_photo_url")
        private String facebook_photo_url;
        @SerializedName("facebook_user")
        private Boolean facebook_user;
        @SerializedName("user_level")
        private String user_level;

//        {
//            "user": {
//            "id": 1,
//                    "name": "Morais Neto",
//                    "email": "moraisnetocc@gmail.com",
//                    "has_password": true,
//                    "profile_photo": null,
//                    "facebook_photo_url": null,
//                    "facebook_user": false,
//                    "user_level": 0
//        },
//            "auth_token": "89f32b813c1261817b100be757e7edf900cc3c57"
//        }

        public User() {
        }

        public User(long id, String name, String email, Boolean has_password, Boolean profile_photo, String facebook_photo_url, Boolean facebook_user, String user_level) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.has_password = has_password;
            this.profile_photo = profile_photo;
            this.facebook_photo_url = facebook_photo_url;
            this.facebook_user = facebook_user;
            this.user_level = user_level;
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

        public Boolean getHas_password() {
            return has_password;
        }

        public Boolean getProfile_photo() {
            return profile_photo;
        }

        public String getFacebook_photo_url() {
            return facebook_photo_url;
        }

        public Boolean getFacebook_user() {
            return facebook_user;
        }

        public String getUser_level() {
            return user_level;
        }
    }

}
