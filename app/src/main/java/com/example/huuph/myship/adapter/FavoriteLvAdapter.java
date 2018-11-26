package com.example.huuph.myship.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huuph.myship.R;
import com.example.huuph.myship.data.model.Datum;
import com.example.huuph.myship.rest.RestClient;
import com.example.huuph.myship.uis.activities.WebViewFabook;
import com.example.huuph.myship.uis.fragment.FragmentNews;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoriteLvAdapter extends ArrayAdapter<Datum> {
    private TextView tvName;
    private TextView tvTime;
    private TextView tvPost;
    private Context context;
    private List<Datum> list;
    private int resource;
    private String nameUserPost;
    private String token;


    public FavoriteLvAdapter(@NonNull Context context, int resource, @NonNull List<Datum> objects, String token) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.list = objects;
        this.token = token;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lv_favorite, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvTime = convertView.findViewById(R.id.tvTime);
            viewHolder.tvPost = convertView.findViewById(R.id.tvPost);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Datum dataNew = list.get(position);
        getUserInfo(viewHolder.tvName,dataNew.getPostid() ,token );
        viewHolder.tvTime.setText(dataNew.getUpdatedTime());
        viewHolder.tvPost.setText(dataNew.getMessage());

        return convertView;
    }

    public class ViewHolder{
        private TextView tvName;
        private TextView tvTime;
        private TextView tvPost;
    }
    public void getUserInfo(final TextView tv, String idfeed, String tokens) {
//        Call<JsonElement> jsonElementCall = RestClient.getAPIs().getUserid(idfeed, "from", tokens);
//        jsonElementCall.enqueue(new Callback<JsonElement>() {
//            @Override
//            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
//                JsonElement jsonElement = response.body();
//                JsonObject jsonObject1 = jsonElement.getAsJsonObject();
//                Log.d("TEST",jsonObject1.toString());
//                JsonObject from = jsonObject1.getAsJsonObject("from");
//                nameUserPost = from.get("name").getAsString();
//            }
//
//            @Override
//            public void onFailure(Call<JsonElement> call, Throwable t) {
//                Log.d("TAG", "fail");
//            }
//        });
        tv.setText("Doan Huu Phuoc");
    }
}
