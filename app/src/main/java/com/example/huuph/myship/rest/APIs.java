package com.example.huuph.myship.rest;

import com.example.huuph.myship.data.model.DataFeed;
import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIs {
    String idPost = null;

    @GET("546129785832997/feed?fields=message,id,updated_time")
    Call<JsonElement> getDrirectionInfo(@Query("access_token") String token);

    @GET("{id}/")
    Call<JsonElement> getUserid(@Path("id") String id,@Query("fields") String fields,@Query("access_token") String token);

    @GET("{id}/")
    Call<JsonElement> getPost(@Path("id") String id,@Query("access_token") String token);
}

