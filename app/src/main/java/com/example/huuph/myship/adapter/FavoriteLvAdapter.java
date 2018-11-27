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
        getPost(viewHolder.tvPost,viewHolder.tvTime,dataNew.getPostid(),token);

        return convertView;
    }

    public class ViewHolder{
        private TextView tvName;
        private TextView tvTime;
        private TextView tvPost;
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
                    nameUserPost = name;
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("TAG", "fail");
            }
        });
        tv.setText(nameUserPost);
    }
}
