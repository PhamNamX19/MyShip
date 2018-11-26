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
import android.widget.Toast;

import com.example.huuph.myship.R;
import com.example.huuph.myship.SQLiteHelper.DatabaseHand;
import com.example.huuph.myship.adapter.FavoriteLvAdapter;
import com.example.huuph.myship.data.model.Datum;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavorite extends Fragment {
    private static FragmentFavorite instance;
    private ListView lvFavorite;
    private List<Datum> favorites;
    private FavoriteLvAdapter adapter;
    private String tokens = "EAADgqSanbEQBAO6NJwr5n6jZCjxoGvYn8hHe9AIyZChXOBpVKpLAFxAqJXzs4YfGJqBFs7I5ibgzMZAuHLeQ5XkZCcHS0k5xmGz5oMJneB6DPcIRtYzZBZAskO83brQtZANgTpQ2YTYaaZAUHN5mKiYgQMKRqE7Xnrr1ZA83u0q13n0gEvWzpSxx6ZCbuW2EuHYPfc5ZCVyQezvrAZDZD";


    public static FragmentFavorite getInstance() {
        if (instance == null) {
            instance = new FragmentFavorite();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_favorite, container, false);
        lvFavorite = view.findViewById(R.id.lvFavorite);
        getDataSaved();
        return view;
    }

    private void getDataSaved() {
        //get data freom database
        DatabaseHand database = new DatabaseHand(getContext());
        List<String> idPosts = database.getAllIDPost();
        Log.d("TAGids",idPosts.toString());
        for (String id : idPosts) {
            Log.d("TAG", "id");
        }
//
//            try {
//            for (String id : idPosts) {
//                Log.d("TAG id","id");
//
//            }
//        } catch (Exception e) {
//            Log.d("TAG", e.getMessage());
//        }


//        Datum data1 = new Datum("Đơn hàng 13 quán thánh đến 254 minh khai. Sdt 01665168295 giá 30k*1","2018-11-14T13:07:13+0000","546129785832997_591759137936728");
//        Datum data2 = new Datum("Đơn hàng 13 quán thánh đến 254 minh khai. Sdt 01665168295 giá 30k*2","2018-11-14T13:07:13+0000","546129785832997_591759137936728");
//        Datum data3 = new Datum("Đơn hàng 13 quán thánh đến 254 minh khai. Sdt 01665168295 giá 30k*3","2018-11-14T13:07:13+0000","546129785832997_591759137936728");
//        Datum data4 = new Datum("Đơn hàng 13 quán thánh đến 254 minh khai. Sdt 01665168295 giá 30k*4","2018-11-14T13:07:13+0000","546129785832997_591759137936728");
//        Datum data5 = new Datum("Đơn hàng 13 quán thánh đến 254 minh khai. Sdt 01665168295 giá 30k*5","2018-11-14T13:07:13+0000","546129785832997_591759137936728");
//        favorites.add(data1);
//        favorites.add(data2);
//        favorites.add(data3);
//        favorites.add(data4);
//        favorites.add(data5);
//        adapter = new FavoriteLvAdapter(getContext(),R.layout.item_lv_favorite,favorites,tokens);
//        lvFavorite.setAdapter(adapter);
    }
}