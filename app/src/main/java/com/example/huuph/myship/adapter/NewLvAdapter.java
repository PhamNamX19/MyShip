package com.example.huuph.myship.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.huuph.myship.R;
import com.example.huuph.myship.uis.FakeDataNew;

import java.util.List;



public class NewLvAdapter extends ArrayAdapter<FakeDataNew> {

    private Context context;
    private int resource;
    private List<FakeDataNew> listData;

    public NewLvAdapter(@NonNull Context context, int resource, @NonNull List<FakeDataNew> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.listData = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvPost = convertView.findViewById(R.id.tvPost);
            viewHolder.tvUser = convertView.findViewById(R.id.tvName);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FakeDataNew dataNew = listData.get(position);


        viewHolder.tvUser.setText(dataNew.getName());
        viewHolder.tvPost.setText(dataNew.getPost());
        return convertView;
    }

    public class ViewHolder{
        TextView tvUser;
        TextView tvPost;
    }
}
