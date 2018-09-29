package com.example.huuph.myship.uis.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.huuph.myship.R;

public class FragmentFavorite extends Fragment {
    private static FragmentFavorite instance;


    public static FragmentFavorite getInstance() {
        if (instance == null){
            instance = new FragmentFavorite();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_favorite,container,false);
        return view;
    }
}