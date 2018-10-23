package com.example.huuph.myship.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.huuph.myship.R;
import com.example.huuph.myship.data.model.Datum;
import com.example.huuph.myship.uis.activities.WebViewFabook;
import com.example.huuph.myship.uis.fragment.FragmentNews;

import java.util.List;


public class NewLvAdapter extends ArrayAdapter<Datum> {

    private Context context;
    private int resource;
    private List<Datum> listData;
    private OnPostItemClickListener itemClickListener;

    public NewLvAdapter(@NonNull Context context, int resource, @NonNull List<Datum> objects, OnPostItemClickListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listData = objects;
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvPost = convertView.findViewById(R.id.tvPost);
            viewHolder.tvUser = convertView.findViewById(R.id.tvName);
            viewHolder.tvTimePost = convertView.findViewById(R.id.tvTimePost);
            viewHolder.btBinhLuon = convertView.findViewById(R.id.btBinhLuan);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Datum dataNew = listData.get(position);
        viewHolder.tvUser.setText(dataNew.getNameUserPost());
        viewHolder.tvPost.setText(dataNew.getMessage());
        viewHolder.tvTimePost.setText(dataNew.getUpdatedTime());
        viewHolder.btBinhLuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onPostItemClick(position);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView tvUser;
        TextView tvPost;
        TextView tvTimePost;
        Button btBinhLuon;
    }

    public interface OnPostItemClickListener {
        void onPostItemClick(int pos);
    }
}
