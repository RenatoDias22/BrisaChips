package com.renatodias.brisachips.Network;

import com.renatodias.brisachips.Fragmants.Cadastro.Model.ImageId;
import com.renatodias.brisachips.Fragmants.Cidades.Model.City;
import com.renatodias.brisachips.Fragmants.Colaboradores.Model.Ponts;
import com.renatodias.brisachips.Fragmants.Home.Model.ColaboradorSuper;
import com.renatodias.brisachips.Fragmants.Regiao.Model.Regioes;
import com.renatodias.brisachips.Login.Model.AuthUser;
import com.renatodias.brisachips.Utils.Constantes;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class NetworkClinet  {

    public interface WebServiceAPI {

        @FormUrlEncoded
        @POST("auth/")
        Call<AuthUser> login(@Field("email") String email,
                             @Field("password") String password);

        @FormUrlEncoded
        @POST("auth/activate_account/")
        Call<AuthUser> redefinSenha(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("new_password") String new_password);

        @GET("orders/get_partners_orders/")
        Call<List<ColaboradorSuper>> getAllPartnersOrders();

        @GET("orders/")
        Call<ColaboradorSuper> getAllOrders();

        @POST("orders/")
        Call<ColaboradorSuper> askForOrdes(@Body HashMap<String, Integer> body);

        @POST("orders/get_orders/")
        Call<ColaboradorSuper> getOrdesColaborador(@Body HashMap<String, Integer> body);

        @POST("orders/accept_order/")
        Call<ColaboradorSuper> askForAcceptOrder(@Body RequestBody body);

        @GET("regions/")
        Call<List<Regioes>> getAllRegions();

        @GET("regions/{pk}/")
        Call<List<City>> getCitys(@Path(value = "pk", encoded = true) String pk);

        @GET("cities/")
        Call<List<City>> getAllCitys();

        @GET("cities/{pk}/")
        Call<List<Ponts>> getAllPonts(@Path(value = "pk", encoded = true) String pk);

        @POST("points/")
        Call<ColaboradorSuper> cadastrarPontoColaborador(@Body RequestBody body);

        @POST("images/")
        Call<ImageId> postImagem(@Body RequestBody image);
    }

    public static WebServiceAPI getNetworkClinet() {

            Retrofit retrofit = new retrofit2.Retrofit
                    .Builder()
                    .baseUrl(Constantes.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        return retrofit.create(WebServiceAPI.class);
    }


    public static WebServiceAPI getAPIWithKey() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Authorization","token " + Constantes.token);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new retrofit2.Retrofit
                .Builder()
                .client(httpClient.build())
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(WebServiceAPI.class);

    }
}
