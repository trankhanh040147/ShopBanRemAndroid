package com.shopeeapp.activity;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.shopeeapp.R;

public class Help extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_help);

        findViewById(R.id.btnBack_help).setOnClickListener(view -> finish());
        WebView webView = findViewById(R.id.webHelp);
        String url = getResources().getString(R.string.help_url);
        webView.loadUrl(url);
    }
}
