package com.example.quanly.retrofit;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientNoti {
    private static Retrofit instance;

    public static  Retrofit getInstance(OkHttpClient client){
        if(instance == null){

            instance = new Retrofit.Builder()
                    .baseUrl("https://fcm.googleapis.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

        }
        return instance;
    }
}
