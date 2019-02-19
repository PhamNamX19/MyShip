package com.example.huuph.myship.uis.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huuph.myship.R;
import com.example.huuph.myship.SQLiteHelper.DatabaseHand;
import com.example.huuph.myship.adapter.FavoriteLvAdapter;
import com.example.huuph.myship.adapter.NewLvAdapter;
import com.example.huuph.myship.data.model.Datum;
import com.example.huuph.myship.rest.RestClient;
import com.example.huuph.myship.uis.activities.WebViewFabook;
import com.example.huuph.myship.ult.StringHandle;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHistory extends Fragment {
    private static FragmentHistory instance;
    private ListView lvFavorite;
    private List<Datum> favorites;
    private NewLvAdapter adapter;
    private Button btXoaTatCa;
    private String token;
    private SwipeRefreshLayout refreshLayout;

    private GestureDetector gestureDetector;


    public static FragmentHistory getInstance() {

        if (instance == null) {
            instance = new FragmentHistory();

        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main_main activity = (main_main) getActivity();
        token = activity.getToken();
        View view = inflater.inflate(R.layout.ui_favorite, container, false);
        lvFavorite = view.findViewById(R.id.lvFavorite);
        refreshLayout = view.findViewById(R.id.swipe_refreshFavo);
        btXoaTatCa = view.findViewById(R.id.btxoatatca);
        btXoaTatCa.setOnClickListener(v -> {
            //Todo Xoa tat ca item da luu
            DatabaseHand database = new DatabaseHand(getContext());
            database.XoaAll();
            getDataSaved();
            lvFavorite.invalidateViews();
            Toast.makeText(getActivity(), "DaXoa", Toast.LENGTH_LONG).show();
        });
        getDataSaved();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        getDataSaved();
                        Log.d("TAG","refreshed layout");
                    }
                },2000);
            }
        });
        return view;
    }

    private void getDataSaved() {
        //get data freom database
        favorites = new ArrayList<>();
        Datum data = new Datum();
        DatabaseHand database = new DatabaseHand(getContext());
        Cursor cursor = database.selectidPost();
        if (cursor.moveToFirst()) {
            do {
                String idPost = cursor.getString(cursor.getColumnIndex("idPost"));
                data.setPostid(idPost);
                favorites.add(data);
            } while (cursor.moveToNext());
        }
        cursor.close();

        adapter = new NewLvAdapter(getContext(), R.layout.item_listview, favorites,
                pos -> {
                    Intent intent = new Intent(getActivity(), WebViewFabook.class);
                    String id = favorites.get(pos).getPostid();
                    int vitri_ = id.indexOf("_");
                    id = id.substring(vitri_ + 1);
                    intent.putExtra("idfeed", id);
                    getActivity().startActivity(intent);
                },
                pos -> {
                    String message1 = favorites.get(pos).getMessage();
                    String phone = StringHandle.searchPhone(message1);
                    phone = "tel:"+phone;
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));
                    startActivity(callIntent);
                    Log.d("TAG", "clicked Call " + phone);
                },
                pos -> {
                    getDataSaved();
                    lvFavorite.invalidateViews();
                    Toast.makeText(getActivity(), "DaXoa", Toast.LENGTH_LONG).show();

                },
                token);
        lvFavorite.setAdapter(adapter);
        lvFavorite.smoothScrollToPosition(0);


    }
    private void getPost(final TextView tvPost, final TextView tvTime, String idfeed, String token){
        Call<JsonElement> call = RestClient.getAPIs().getPost(idfeed,token);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                Log.d("TAG",jsonObject.toString());
                String message = jsonObject.get("message").getAsString();
                String  time = jsonObject.get("created_time").getAsString();
                ///
                String day = time.substring(8, 10) + "-" + time.substring(5, 7) + "-" + time.substring(0, 4);
                //format time
                int vitri_T = time.indexOf("T");
                String hour = time.substring(vitri_T + 1, vitri_T + 3);
                int h = Integer.parseInt(hour);
                h = h + 7;
                hour = h + "";
                time = day + "  " + hour + time.substring(vitri_T + 3, vitri_T + 6);
                ////
                tvPost.setText(message);
                tvTime.setText(time);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("TAG","fail call getPost");
            }
        });

    }
    private void getUserInfo(final TextView tv, String idfeed, String tokens) {
        Call<JsonElement> jsonElementCall = RestClient.getAPIs().getUserid(idfeed, "from", tokens);
        jsonElementCall.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement jsonElement = response.body();
                JsonObject jsonObject1 = jsonElement.getAsJsonObject();

                if (jsonObject1.getAsJsonObject("from") != null) {
                    JsonObject from = jsonObject1.getAsJsonObject("from");
                    String name = from.get("name").getAsString();
                    Log.d("names", name);

                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("TAG", "fail");
            }
        });

    }

}