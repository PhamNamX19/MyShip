package com.example.huuph.myship.uis.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.huuph.myship.R;
import com.example.huuph.myship.adapter.NewLvAdapter;
import com.example.huuph.myship.data.model.Datum;
import com.example.huuph.myship.rest.RestClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNews extends Fragment {
    private static FragmentNews instance;
    private int number = 0;
    private ListView lvNew;
    private List<Datum> dataNews;
    private NewLvAdapter adapter;
    private String tokens = "EAAhqYFrQulMBAFZAsl2SKbZADiC5VPEtl4dV9XwtmR2xsqY30JshXgVt78RXNLyeNPRIbzN1vvMu8BrLRAVgIhFiNTnqQGIpYeBkL0uga8OBVyWfeV3wKuH7gInUytv7ps0yD6fB6LnQSWFdQ9WiSsZCZC14MhomxUZBHgvx23L8FTZCaIVb0mZAs1zzWDRqlWailw3fmlq1ZBZAwZBE7l40cBgUecKZBq1EQsZD";

    private String id;
    private String message;
    private String updatedTime;

    public static FragmentNews getInstance(String dataSent) {
        if (instance == null) {
            instance = new FragmentNews();
            Log.d("TAG",dataSent);
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_news, container, false);
        lvNew = view.findViewById(R.id.lvNew);
        dataNews = new ArrayList<>();
        getDataFeed();
        return view;


    }

    //get data json
    private void getDataFeed() {

        Call<JsonElement> call = RestClient.getAPIs().getDrirectionInfo(tokens);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonElement jsonElement = response.body();
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                JsonArray datums = jsonObject.getAsJsonArray("data");
                for (int i = 0; i < datums.size(); i++) {

                    ///trycath
                    JsonObject datal = datums.get(i).getAsJsonObject();
                    id = datal.get("id").getAsString();
                    message = datal.get("message").getAsString();
                    updatedTime = datal.get("updated_time").getAsString();

                    //test @path
                    Call<JsonElement> jsonElementCall = RestClient.getAPIs().getUserid(id,"from",tokens);
                    jsonElementCall.enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            JsonElement jsonElement1 = response.body();
                            Log.d("TAG",jsonElement1.toString());
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            Log.d("TAG", "fail");

                        }
                    });


                    //add datal vao list
                    Log.d("info", id);
                    Log.d("info", message);
                    Log.d("info", updatedTime);
                    Datum datas = new Datum(id, message, updatedTime);
                    dataNews.add(datas);
                    adapter = new NewLvAdapter(getContext(), R.layout.item_listview, dataNews);
                    lvNew.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });


    }

}