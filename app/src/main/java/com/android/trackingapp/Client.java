package com.android.trackingapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by MohamedHamdy on 6/5/2018.
 */

public class Client {
    private static Retrofit client;

    public static Retrofit getInstance(String baseUrl){
        if(client == null){
            client = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return client;
    }
}
