package com.example.huuph.myship.rest;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIs {
       public interface APIsgetFeed {
        @GET("546129785832997?fields=feed%7Bid%2Cmessage%2Cupdated_time%7D")
        Call<JsonElement> getWeatherInfo(@Query("access_token") String token);

    }
    public interface APIsgetUserPost {
        @GET("546129785832997?fields=feed%7Bid%2Cmessage%2Cupdated_time%7D")
        Call<JsonElement> getWeatherInfo(@Query("access_token") String token);

    }



}

