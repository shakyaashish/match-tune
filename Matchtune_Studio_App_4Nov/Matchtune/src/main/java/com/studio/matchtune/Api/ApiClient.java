package com.studio.matchtune.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public  static Retrofit RETROFIT = null;
    public static final  String API_BASE_URL ="https://api.matchtune.com/";

    public  static Retrofit getclient(){

        if(RETROFIT==null) {

            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            Gson gson = new GsonBuilder().create();

            RETROFIT = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();


        }
        return  RETROFIT;
    }

}
