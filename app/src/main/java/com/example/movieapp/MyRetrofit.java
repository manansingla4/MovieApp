package com.example.movieapp;

import com.example.movieapp.Values.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    private static Retrofit mRetrofit;

    public static Retrofit getRetrofitInstance() {
        if(mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(URL.getBaseUrl())
                    .build();
        }
        return mRetrofit;
    }
}
