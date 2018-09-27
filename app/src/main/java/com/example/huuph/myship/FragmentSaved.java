package com.example.huuph.myship;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentSaved extends Fragment {
    private static FragmentSaved instance;


    public static FragmentSaved getInstance() {
        if (instance == null){
            instance = new FragmentSaved();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_saved,container,false);
        return view;
    }
}
