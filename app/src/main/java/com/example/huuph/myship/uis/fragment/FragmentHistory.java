package com.example.huuph.myship.uis.fragment;

import android.database.Cursor;
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
import android.widget.Toast;

import com.example.huuph.myship.R;
import com.example.huuph.myship.SQLiteHelper.DatabaseHand;
import com.example.huuph.myship.adapter.FavoriteLvAdapter;
import com.example.huuph.myship.data.model.Datum;

import java.util.ArrayList;
import java.util.List;

public class FragmentHistory extends Fragment {
    private static FragmentHistory instance;
    private ListView lvFavorite;
    private List<Datum> favorites;
    private FavoriteLvAdapter adapter;
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
        btXoaTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo Xoa tat ca item da luu
                DatabaseHand database = new DatabaseHand(getContext());
                database.XoaAll();
                getDataSaved();
                lvFavorite.invalidateViews();
                Toast.makeText(getActivity(), "DaXoa", Toast.LENGTH_LONG).show();
            }


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

        adapter = new FavoriteLvAdapter(getContext(), R.layout.item_lv_favorite, favorites, token);
        lvFavorite.setAdapter(adapter);
        lvFavorite.smoothScrollToPosition(0);


    }


}