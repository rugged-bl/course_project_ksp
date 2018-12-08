package com.data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ApiServiceProvider {
    private static volatile ApiService instance;

    public static ApiService getInstance() {
        ApiService localInstance = instance;
        if (localInstance == null) {
            synchronized (ApiService.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = provideApiService(provideRetrofit(provideOkHttpClient()));
                }
            }
        }
        return localInstance;
    }

    private static ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    private static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl("http://localhost:8080/")
                .build();
    }

    private static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }
}
