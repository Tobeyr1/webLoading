package com.tobery.weblib

import android.annotation.SuppressLint
import android.app.Activity
import android.webkit.WebSettings
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar

/**
 * @Package: com.tobey.webviewlibrary
 * @ClassName: WebViewSettings
 * @Author: Tobey_r1
 * @CreateDate: 2021/3/19 17:40
 * @Description: java类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/19 17:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
object WebViewSettings {

    @SuppressLint("SetJavaScriptEnabled")
    @JvmStatic
    fun setWebSettings(webView: WebView, activity: Activity) {
        val webSettings = webView.settings
        webSettings.apply {
            setSupportZoom(true)
            useWideViewPort = true
            allowFileAccess = true
            defaultTextEncodingName = "utf-8"
            loadsImagesAutomatically = true
            loadWithOverviewMode = true
            javaScriptEnabled = true
        }
        saveData(webSettings, activity)
        newWin(webSettings)
    }

    fun setProgress(webView: WebView, progressBar: ProgressBar?) {
        webView.webChromeClient = webChromeClient
    }

    private val webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }
    }

    /**
     * 多窗口的问题
     */
    private fun newWin(webSettings: WebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        webSettings.setSupportMultipleWindows(false)
        webSettings.javaScriptCanOpenWindowsAutomatically = true
    }

    @JvmStatic
    fun onDestroy(mWebView: WebView?) {
        mWebView?.let {
            it.clearHistory()
            it.removeView(it)
            it.loadUrl("about:blank")
            it.stopLoading()
            it.webChromeClient = null
            it.destroy()
        }
    }

    /**
     * HTML5数据存储
     */
    private fun saveData(webSettings: WebSettings, activity: Activity) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.setAppCacheEnabled(true)
        val appCachePath = activity.applicationContext.cacheDir.absolutePath
        webSettings.setAppCachePath(appCachePath)
    }

    fun removeDiv(div: String): String {
        return "javascript:function removeDiv() {" +
                "var bars = document.getElementsByClassName('" + div + "');" +
                "}"
    }
}