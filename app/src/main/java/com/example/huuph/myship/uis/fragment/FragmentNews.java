package com.example.huuph.myship.uis.fragment;

import android.content.Intent;
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
import com.example.huuph.myship.uis.activities.WebViewFabook;
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
    private String tokens = "EAAGxzui9ezkBAMEGsZBlTGJ3NWF5Um42EquUjPy1K3kULVvuYoqZB8wpMxc64wCE3bdI098vZCB4FneAWrL9sBv8ZAmj09wCZAbl4xGWrDCyj9Shjp8YCbrvVLcuhsJYZAMyd0dWHwmX2NcGRhXKDkgmDjgW6eDlZCjMqLIueOe6zES5ZCYR5TcBylqafZAIM0a4QDNbZAzz2S4gZDZD";

    private String idfeed;
    private String message;
    private String updatedTime;
    private String idUserPost;
    private String nameUserPost;

    public static FragmentNews getInstance() {
        if (instance == null) {
            instance = new FragmentNews();

        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_news, container, false);
        lvNew = view.findViewById(R.id.lvNew);
        dataNews = new ArrayList<>();


        main_main activity = (main_main) getActivity();
        String token = activity.getToken();


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
                    idfeed = datal.get("id").getAsString();
                    message = datal.get("message").getAsString();
                    updatedTime = datal.get("updated_time").getAsString();

                    //test @path
                    Call<JsonElement> jsonElementCall = RestClient.getAPIs().getUserid(idfeed, "from", tokens);
                    jsonElementCall.enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            JsonElement jsonElement = response.body();
                            JsonObject jsonObject1 = jsonElement.getAsJsonObject();
                            JsonObject from = jsonObject1.getAsJsonObject("from");
                            idUserPost = from.get("id").getAsString();
                            nameUserPost = from.get("name").getAsString();

                            Log.d("TAG", nameUserPost+idfeed);

                            Datum datas = new Datum(nameUserPost, message, updatedTime);
                            dataNews.add(datas);
                            adapter = new NewLvAdapter(getContext(), R.layout.item_listview, dataNews, new NewLvAdapter.OnPostItemClickListener() {
                                @Override
                                public void onPostItemClick(int pos) {
                                    getActivity().startActivity(new Intent(getActivity(), WebViewFabook.class));
                                }
                            });
                            lvNew.setAdapter(adapter);

                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            Log.d("TAG", "fail");

                        }
                    });


                    //add datal vao list
//                    Log.d("info", nameUserPost);
//                    Log.d("info", message);
//                    Log.d("info", updatedTime);
                   /* Datum datas = new Datum(idfeed, message, updatedTime);
                    dataNews.add(datas);
                    adapter = new NewLvAdapter(getContext(), R.layout.item_listview, dataNews, new NewLvAdapter.OnPostItemClickListener() {
                        @Override
                        public void onPostItemClick(int pos) {
                            getActivity().startActivity(new Intent(getActivity(), WebViewFabook.class));
                        }
                    });
                    lvNew.setAdapter(adapter);*/

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });


    }


}