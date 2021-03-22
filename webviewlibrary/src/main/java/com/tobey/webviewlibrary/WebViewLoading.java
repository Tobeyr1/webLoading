package com.tobey.webviewlibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;

import java.util.ArrayList;
import java.util.List;


/**
 * @Package: com.tobey.webviewlibrary
 * @ClassName: WebViewLoading
 * @Author: Tobey_r1
 * @CreateDate: 2021/3/21 15:42
 * @Description: 封装webview
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/21 15:42
 * @UpdateRemark:
 * @Version: 1.0
 */
public class WebViewLoading  extends RelativeLayout {

    Context context;
    ProgressBar progressBar;
    WebView webView;
    private static String currentUrl;
    private static String javascript = "";
    private static ArrayList<String> js = new ArrayList<>();
    List<String> list;

    public WebViewLoading(@NonNull Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public WebViewLoading(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public WebViewLoading(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_webprogress, null);
        progressBar = inflate.findViewById(R.id.progressBar);
        webView = inflate.findViewById(R.id.webView);
        addView(inflate);
    }

    /**
     * 基本设置
     * @param activity 活动
     */
    public void setSettings(Activity activity){
       WebViewSettings.setWebSettings(webView,activity);
       intClient();
    }

    /**
     * 进度条加载颜色设置
     * @param drawable 渲染
     */
    public void setProgressColor(Drawable drawable){
       progressBar.setProgressDrawable(drawable);
    }

    private void intClient() {
        webView.setWebChromeClient(webChromeClient);
        webView.setWebViewClient(webViewClient);
    }

    WebChromeClient webChromeClient = new WebChromeClient(){
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if(newProgress==100){
                progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                webView.setVisibility(View.VISIBLE);
            }
            else{
                webView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                progressBar.setProgress(newProgress);//设置进度值
            }
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            Log.e("文件操作",fileChooserParams.toString());

            return true;
        }


        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(view);
            resultMsg.sendToTarget();
            return true;
        }
    };

    WebViewClient webViewClient = new WebViewClient(){

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
           if (!javascript.equals("")){
               view.loadUrl(javascript);
           }
            /*if (js.size()!=0){
                view.loadUrl(javascript);
            }*/


    }

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        //解决证书问题
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
           handler.proceed();
        }
    };


    /**
     * 提供页面返回方法
     * @param  activity 活动
     */
    public void goback(Activity activity){
        if (webView.canGoBack()&&(!webView.getUrl().equals(currentUrl))){
            webView.goBack();
        }else {
            activity.finish();
        }
    }

    /**
     * 提供自定义WebChromeClient接口
     * @param  webChromeClient 回调
     */
    public void setCromeClient(WebChromeClient webChromeClient){
        webView.setWebChromeClient(webChromeClient);
    }

    /**
     * 提供自定义WebViewClient接口
     * @param  client 回调
     */
    public void setCromeClient(WebViewClient client){
        webView.setWebViewClient(client);
    }

    /**
     * 去除网页元素
     * @param divName 要去除的元素id
     */
    public void removeDiv(String divName){
        javascript = "javascript:function removeDiv() {" +
                "var divs = document.getElementsByClassName('"+divName+"');" +
                "var firstdiv = divs[0];" +
                "firstdiv.remove();" + "}"+
                "javascript:removeDiv();";
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public  List<String> getData(){

        list = Stream.of(list).filter(new Predicate<String>() {
            @Override
            public boolean test(String value) {
                return !"".equals(value);
            }
        }).collect(Collectors.toList());

        return list;
    }

/*    *//**
     * 去除网页元素
     * @param divName 要去除的元素对象
     *//*
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void removeDiv(List divName){
        list = divName;
        getData();
        int size = list.size();
        int i =0;
        String s ="";
        for (String jsItem: list){
            i++;
            js.add("var divs"+i+" = document.getElementsByClassName('"+jsItem+"')[0];"+
                    "divs"+i+".remove();");
            s+= js.get(i-1);
        }
        javascript = "javascript:function removeDiv() {" +
                "var arr = "+list+";"+
                "for(var j=0;j<="+size+";j++){"+
                "var child = document.getElementsByClassName('arr[j]')[0];"+
                "child.remove();"+"}"+
                "}"+"javascript:removeDiv();";

        System.out.println("拼接数据"+javascript);

    }*/

    /**
     * 页面销毁时调用此方法
     */
    public void setDestory(){
        WebViewSettings.onDestroy(webView);
    }

    /**
     * 页面恢复时调用
     */
    public void setResume(){
        webView.onResume();
        webView.resumeTimers();
    }


    /**
     * 父布局暂停时调用此方法
     */
    public void setPause(){
        webView.onPause();
        webView.pauseTimers(); //小心这个！！！暂停整个 WebView 所有布局、解析、JS。
    }





    /**
     * 设置加载网址
     * @param url 链接
     */
    public void setUrl(String url){
        currentUrl =url;
          webView.loadUrl(url);
    }

    /**
     * 设置加载网址
     * @param url 链接
     */
    public void setUrl(Uri url){
        currentUrl =url.toString();
        webView.loadUrl(url.toString());
    }

    /**
     * 获取当前webview实例
     */
    public WebView getWebView(){
        return webView;
    }

    /**
     * 获取当前webviewclient实例
     */
    public WebViewClient getWebViewClient(){
        return webViewClient;
    }

    /**
     * 获取当前WebChromeClient实例
     */
    public WebChromeClient getWebChromeClient(){
        return webChromeClient;
    }
}
