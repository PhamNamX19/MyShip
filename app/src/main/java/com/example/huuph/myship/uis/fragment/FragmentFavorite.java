package com.example.huuph.myship.uis.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.huuph.myship.R;
import com.example.huuph.myship.SQLiteHelper.DatabaseHand;
import com.example.huuph.myship.UserManager;
import com.example.huuph.myship.adapter.FavoriteLvAdapter;
import com.example.huuph.myship.data.model.Datum;
import com.example.huuph.myship.rest.RestClient;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class FragmentFavorite extends Fragment {
    private static FragmentFavorite instance;
    private ListView lvFavorite;
    private List<Datum> favorites;
    private FavoriteLvAdapter adapter;
    private Button btXoaTatCa;
    private String token;


    public static FragmentFavorite getInstance() {
        if (instance == null) {
            instance = new FragmentFavorite();
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
        btXoaTatCa = view.findViewById(R.id.btxoatatca);
        btXoaTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo Xoa tat ca item da luu

                lvFavorite.invalidateViews();
                Toast.makeText(getActivity(), "DaXoa", Toast.LENGTH_LONG).show();
            }
        });
        getDataSaved();
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

        adapter = new FavoriteLvAdapter(getContext(), R.layout.item_lv_favorite, favorites, token);
        lvFavorite.setAdapter(adapter);


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
    }

}