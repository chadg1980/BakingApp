package com.h.chad.bakingapp.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chad on 7/6/2017.
 *
 * This is the Retrofit interface
 *
 * */

public class NetworkClient {
    private static Retrofit mRetrofit = null;

    public static Retrofit getClient(String baseUrl){
        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return mRetrofit;
    }
}
