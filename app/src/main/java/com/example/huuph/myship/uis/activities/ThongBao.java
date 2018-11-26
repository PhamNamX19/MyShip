package com.example.huuph.myship.uis.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.huuph.myship.R;

public class ThongBao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_bao);

    }
    public void Back(View view) {
        finish();

    }
}
