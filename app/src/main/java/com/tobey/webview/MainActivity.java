package com.tobey.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tobey.webviewlibrary.WebViewLoading;

public class MainActivity extends AppCompatActivity {

    WebViewLoading webViewLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webViewLoading = findViewById(R.id.web);
        webViewLoading.setUrl("https://blog.csdn.net/Tobey_r1");
        webViewLoading.setSettings(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webViewLoading.setPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webViewLoading.setDestory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webViewLoading.setResume();
    }
}
