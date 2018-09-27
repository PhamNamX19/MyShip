package com.example.huuph.myship;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentNews extends Fragment {
    private static FragmentNews instance;
    private TextView tvNumber;
    private int number = 0;

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
        innit();
        return view;
    }

    private void innit() {
        tvNumber = getView().findViewById(R.id.tv_number);
    }

    public void setNumber() {
        number++;
        tvNumber.setText(number + "");
    }
}