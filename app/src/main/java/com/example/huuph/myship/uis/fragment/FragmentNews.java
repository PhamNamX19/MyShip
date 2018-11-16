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
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private String tokens = "EAAGxzui9ezkBAHZB6ZCQ5kzppOdrrq6rL7mABCZBB0fjn7IErf909dYwy0JHRGFjhZBd8O8KWF56zefOjupcDVDwGZC4TV2KFq3hOkNltrtw0pYXEuThAoofCHraZAysNLeTQwIkRLZCgjObAwmNXdNxxc5Y4ZCaEgPjrNOzRL0TVHSxwfy8L1UT8s57JVloaCYZD";
    private DatabaseReference ShipReference;

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
//        //initial fire base real time database
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        ShipReference = firebaseDatabase.getReference("fir-7bfe4");
//        getListNewShip();


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
                    String idfeed = datal.get("id").getAsString();
                    String message = datal.get("message").getAsString();
                    String updatedTime = datal.get("updated_time").getAsString();
                    Datum datas = new Datum(message, updatedTime, idfeed);
                    dataNews.add(datas);

                    //log
                    Log.d("TAG", idfeed + message + updatedTime);

                    adapter = new NewLvAdapter(getContext(), R.layout.item_listview, dataNews, new NewLvAdapter.OnPostItemClickListener() {
                        @Override
                        public void onPostItemClick(int pos) {
                            Intent intent = new Intent(getActivity(), WebViewFabook.class);
                            intent.putExtra("idfeed", dataNews.get(pos).getPostid());
                            getActivity().startActivity(intent);
                        }
                    }, tokens);
                    lvNew.setAdapter(adapter);


                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getListNewShip() {
        ShipReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataNews.clear();
                for(DataSnapshot postSnapshot1 : dataSnapshot.getChildren()){
                    Datum dataShip =postSnapshot1.getValue(Datum.class);
                    dataNews.add(dataShip);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}