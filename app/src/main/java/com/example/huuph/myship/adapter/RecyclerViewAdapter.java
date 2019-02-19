package com.example.huuph.myship.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.huuph.myship.R;
import com.example.huuph.myship.data.model.Datum;
import com.example.huuph.myship.rest.RestClient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private String nameUserPost = "Name";
    private List<Datum> listData;
    private OnPostItemClickListener itemFaceListener;
    private OnPostItemClickListener itemCallListener;
    private OnPostItemClickListener itemSaveListener;
    private String token;

    public RecyclerViewAdapter(@NonNull Context context, int resource, @NonNull List<Datum> objects
            , OnPostItemClickListener listenerFace
            , OnPostItemClickListener listenerCall
            , OnPostItemClickListener listenerSave
            , String token) {


        this.context = context;
        int resource1 = resource;
        this.listData = objects;
        this.token = token;

        this.itemFaceListener = listenerFace;
        this.itemCallListener = listenerCall;
        this.itemSaveListener = listenerSave;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.item_listview, parent, false)
        );

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Datum dataNew = listData.get(position);

        holder.tvPost.setText(dataNew.getMessage());
        holder.tvTimePost.setText(dataNew.getUpdatedTime());
        holder.tvUser.setText(getUserInfo(dataNew.getPostid(), token)
        );
        //add listener
        holder.btBinhLuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemFaceListener.onPostItemClick(position);
                // Toast.makeText(context, "adsad"+position, Toast.LENGTH_SHORT).show();
            }
        });
        //add listener
        holder.btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemCallListener.onPostItemClick(position);
            }
        });
        holder.btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemSaveListener.onPostItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUser;
        TextView tvPost;
        TextView tvTimePost;
        Button btBinhLuon;
        Button btCall;
        Button btSave;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvPost = itemView.findViewById(R.id.tvPost);
            this.tvUser = itemView.findViewById(R.id.tvName);
            this.tvTimePost = itemView.findViewById(R.id.tvTimePost);
            this.btBinhLuon = itemView.findViewById(R.id.btBinhLuan);
            this.btCall = itemView.findViewById(R.id.btCall);
            this.btSave = itemView.findViewById(R.id.btSave);
        }
    }

    public interface OnPostItemClickListener {
        void onPostItemClick(int pos);
    }

    private String getUserInfo(String idfeed, String tokens) {
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
        return nameUserPost;

    }
}
