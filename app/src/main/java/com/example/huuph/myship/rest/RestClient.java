package com.example.huuph.myship.rest;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static Retrofit retrofit;
    private static final String BASE_API1 = "https://graph.facebook.com/v3.1/";
    private static final String BASE_API2 = "https://graph.facebook.com/v3.1/";

    public static APIs.APIsgetFeed APIsgetFeed() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(APIs.APIsgetFeed.class);
    }
    public static APIs.APIsgetUserPost APIsgetUserPost() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(APIs.APIsgetUserPost.class);
    }
}

