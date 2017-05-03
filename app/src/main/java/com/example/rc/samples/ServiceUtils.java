package com.example.rc.samples;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceUtils {

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(AppConfig.HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
