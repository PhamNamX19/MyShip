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
        Log.d("idfeds", idfeed);
        webView = findViewById(R.id.webView);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl("https://m.facebook.com/groups/546129785832997?view=permalink&id=" + idfeed + "&__tn__=%2As-R");
        webView.setWebViewClient(new WebViewClient());


    }

    public void back(View view) {
        finish();

    }


}
