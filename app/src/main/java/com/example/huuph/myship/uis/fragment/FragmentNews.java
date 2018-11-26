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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private ListView lvNew;
    private List<Datum> dataNews;
    private NewLvAdapter adapter;
    private String token;

    //   private String tokens = "EAAGxzui9ezkBAE1pbjnGw4DUBzrTWlhDvtkxWZCD7kUWCbWiZBnMdth8iLhhqV40IyanSpgzAY1ZB3mwsIcnwluZBrY72FZBZCqpzJsZBxZAZCu7IQJRBL8n4oWh0E4T9fBeokJHOPyKVExpFeOKRIWSfB5sJt6ta1900EubFihWP6YF9XDZB3qBdBhVZBLddLWYIUZD";


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
        token = activity.getToken();
        Log.d("token", "new" + token);
        getDataFeed();

        return view;

    }

    //get data json
    private void getDataFeed() {

        Call<JsonElement> call = RestClient.getAPIs().getDrirectionInfo(token);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                final JsonElement jsonElement = response.body();
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonArray datums = jsonObject.getAsJsonArray("data");


                for (int i = 0; i < datums.size(); i++) {
                    JsonObject datal = datums.get(i).getAsJsonObject();

                    if (datal.get("id") != null &&
                            datal.get("message") != null &&
                            datal.get("updated_time") != null) {

                        String idfeed = datal.get("id").getAsString();
                        String message = datal.get("message").getAsString();
                        String updatedTime = datal.get("updated_time").getAsString();
                        //2018-11-26T04:14:52+0000
                        //2018-11-26T04:14:52
                        //format day
                        String day = updatedTime.substring(8, 10) + "-" + updatedTime.substring(5, 7) + "-" + updatedTime.substring(0, 4);
                        //format time
                        int vitri_T = updatedTime.indexOf("T");
                        String hour = updatedTime.substring(vitri_T + 1, vitri_T + 3);
                        int h = Integer.parseInt(hour);
                        h = h + 7;
                        hour = h + "";
                        updatedTime = day + "  " + hour + updatedTime.substring(vitri_T + 3, vitri_T + 6);
                        Datum datas = new Datum(message, updatedTime, idfeed);
                        // WriteDatabase(message, updatedTime, idfeed);
                        dataNews.add(datas);

                        //log
                        Log.d("TAG", idfeed + message + updatedTime);

                        adapter = new NewLvAdapter(getContext(), R.layout.item_listview, dataNews, new NewLvAdapter.OnPostItemClickListener() {
                            @Override
                            public void onPostItemClick(int pos) {
                                Intent intent = new Intent(getActivity(), WebViewFabook.class);
                                String id = dataNews.get(pos).getPostid();
                                int vitri_ = id.indexOf("_");
                                id = id.substring(vitri_ + 1);
                                intent.putExtra("idfeed", id);

                                getActivity().startActivity(intent);
                            }
                        }, token);
                        lvNew.setAdapter(adapter);


                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    public void WriteDatabase(String message, String updatedTime, String postid) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("POST");
        Datum data1 = new Datum(message, updatedTime, postid);
        // myRef.push().setValue(data1);
        myRef.child(postid).setValue(data1);


    }


}