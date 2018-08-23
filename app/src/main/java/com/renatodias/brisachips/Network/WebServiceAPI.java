package com.renatodias.brisachips.Network;

import com.renatodias.brisachips.Fragmants.Colaboradores.Model.Colaborador;
import com.renatodias.brisachips.Login.Model.AuthUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServiceAPI {

    @FormUrlEncoded
    @POST("auth/")
    Call<AuthUser> login(@Field("email") String email,
                         @Field("password") String password);

    @GET("/Clientes")
    Call<List<Colaborador>> getAllColaboradores();

}
