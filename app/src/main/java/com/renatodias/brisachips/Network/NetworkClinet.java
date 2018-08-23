package com.renatodias.brisachips.Network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClinet  {

    private static Retrofit networkClinet;
    public static String token;
    private static final String BASE_URL = "http://chips.brisanet.net.br/api/";

    public static Retrofit getNetworkClinet() {
        if (networkClinet == null) {
            networkClinet = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return networkClinet;
    }

//    public AuthAPI getAPI() {
//        Retrofit retrofit = new Retrofit
//                .Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return retrofit.create(AuthAPI.class);
//
//    }

    public NetworkClinet getAPIWithKey() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("Authorization", "Token " + token).build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit
                .Builder()
                .client(httpClient.build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NetworkClinet.class);

    }
}
