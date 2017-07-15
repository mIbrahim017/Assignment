package com.app.assignment.repository.remote;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

/**
 * Created by mohamed.ibrahim on 7/5/2017.
 */

public class APIsFactory {


    public static APIsServices createInstance(String BASE_URL) {
        return new Retrofit.Builder().baseUrl(BASE_URL).client(newClient())
                .addConverterFactory(GsonConverterFactory.create(newGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(APIsServices.class);
    }

    private static Gson newGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }


    private static OkHttpClient newClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS).
                addInterceptor(new HttpLoggingInterceptor().setLevel(BODY));


        return builder.build();


    }


}
