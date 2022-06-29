package com.tobey.webview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tobey.webviewlibrary.WebViewLoading;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    WebViewLoading webViewLoading;

   private ArrayList<String> list = new ArrayList<>();

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webViewLoading = findViewById(R.id.web);
        webViewLoading.setSettings(this);

        list.add("colu_author_t");
        list.add("colu_author_c");
        list.add("csdn-toolbar");
        list.add("");
      //  webViewLoading.removeDiv(list);
       webViewLoading.removeDiv("csdn-toolbar");
        webViewLoading.setUrl("https://y.music.163.com/m/at/62abf4e67987b3daca41e8f0?market=banner");
        Log.e("标题",webViewLoading.getWebTitle());
    //   webViewLoading.setProgressColor(getResources().getDrawable(R.drawable.pg));
     //   webViewLoading.setProgressColor(getResources().getDrawable(R.color.colorAccent));
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webViewLoading.goback(MainActivity.this);
            }
        });
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
