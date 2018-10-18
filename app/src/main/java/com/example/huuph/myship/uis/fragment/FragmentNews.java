package com.example.huuph.myship.uis.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.huuph.myship.R;
import com.example.huuph.myship.adapter.NewLvAdapter;
import com.example.huuph.myship.uis.FakeDataNew;

import java.util.ArrayList;
import java.util.List;

public class FragmentNews extends Fragment {
    private static FragmentNews instance;
    private int number = 0;
    private ListView lvNew;
    private List<FakeDataNew> dataNews;
    private NewLvAdapter adapter;
    public static FragmentNews getInstance() {
        if (instance == null) {
            instance = new FragmentNews();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_news,container,false);
        lvNew = view.findViewById(R.id.lvNew);
        dataNews = new ArrayList<>();
        FakeDataNew data1 = new FakeDataNew("Đoàn Hữu Phước","post ở đây");
        dataNews.add(data1);


        adapter = new NewLvAdapter(getContext(),R.layout.item_listview,dataNews);
        lvNew.setAdapter(adapter);
        return view;
    }

}