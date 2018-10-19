package com.example.huuph.myship.rest;

import com.example.huuph.myship.data.model.DataFeed;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIs {
    @GET(" 546129785832997/feed?fields=id%2Cmessage%2Cupdated_time")
    Call<JsonElement> getDrirectionInfo(@Query("access_token") String token);


}


//    public interface APIsgetUserPost {
//        @GET("546129785832997?fields=feed%7Bid%2Cmessage%2Cupdated_time%7D")
//        Call<JsonElement> getUserPostInfo(@Query("access_token") String token);
//
//    }





