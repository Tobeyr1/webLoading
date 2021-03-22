package com.tobey.webviewlibrary;

import android.app.Activity;
import android.os.Build;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
public class WebViewSettings {

    public static  void setWebSettings(WebView webView,Activity activity){
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setLoadsImagesAutomatically(true);
        //自适应屏幕
        //  webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        //设置是否出现缩放工具
        //   webSettings.setBuiltInZoomControls(true);
        //调用JS方法.安卓版本大于17,加上注解 @JavascriptInterface
        webSettings.setJavaScriptEnabled(true);
        saveData(webSettings,activity);
        newWin(webSettings);

    }

    public static void  setProgress(WebView webView, ProgressBar progressBar){
               webView.setWebChromeClient(webChromeClient);
    }

    static WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    };

    /**
     * 多窗口的问题
     */
    private static void newWin(WebSettings webSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        webSettings.setSupportMultipleWindows(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }




    public static void onDestroy( WebView mWebView){
        if (mWebView != null) {
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebChromeClient(null);
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }

    }



    /**
     * HTML5数据存储
     */
    private static void saveData(WebSettings webSettings, Activity activity) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        String appCachePath = activity.getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
    }


    public static String removeDiv(String div) {

        String javascript = "javascript:function removeDiv() {" +
                "var bars = document.getElementsByClassName('"+div+"');" +
                "}";
        return javascript;
    }


}
