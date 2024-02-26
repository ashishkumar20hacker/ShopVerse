package com.productexplorer.anandroidadventure.Retrofit;

import com.productexplorer.anandroidadventure.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFit_APIClient {
    public static RetroFit_APIClient apiClient;
    private Retrofit retrofit = null;

    public static RetroFit_APIClient getInstance() {
        if (apiClient == null) {
            apiClient = new RetroFit_APIClient();
        }
        return apiClient;
    }

    public Retrofit getClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);   // development build
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);    // production build
        }
        client.addInterceptor(interceptor);
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder().baseUrl("https://dummyjson.com/").client(client.build()).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit;
    }
}
