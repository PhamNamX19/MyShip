package com.example.huuph.myship.uis.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.huuph.myship.R;
import com.example.huuph.myship.uis.fragment.main_main;

public class WebViewFabook extends AppCompatActivity {
    WebView webView;
    String idfeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_fabook);
        idfeed = getIntent().getStringExtra("idfeed");

        webView = findViewById(R.id.webView);
        webView.loadUrl("https://m.facebook.com/" + idfeed + "");
        webView.setWebViewClient(new WebViewClient());
        Log.d("TT", idfeed);


    }

    public void back(View view) {
        finish();
        Intent intent = new Intent(this,main_main.class);
        startActivity(intent);
    }


}
