package com.tobery.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.tobery.weblib.WebViewLoading;

public class JavaActivity extends AppCompatActivity {

    WebViewLoading webViewLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        webViewLoading = findViewById(R.id.web);
        webViewLoading.setSettings(this);
        webViewLoading.getTitle(title ->{
            Log.e("标题",title);
            return null;
        });
        webViewLoading.setUrl("https://y.music.163.com/m/at/62abf4e67987b3daca41e8f0?market=banner");
    }
}