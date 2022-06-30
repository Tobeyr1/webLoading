package com.tobery.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tobery.weblib.WebViewLoading

class MainActivity : AppCompatActivity() {
    private var webViewLoading: WebViewLoading? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webViewLoading = findViewById(R.id.web)
        webViewLoading?.setSettings(this)
        webViewLoading?.getTitle {
            //获取标题
        }
        webViewLoading?.setUrl("https://y.music.163.com/m/at/62abf4e67987b3daca41e8f0?market=banner")
    }
}