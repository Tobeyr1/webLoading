package com.tobey.webviewlibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

/**
 * @Package: com.tobey.webviewlibrary
 * @ClassName: WebViewLoading
 * @Author: Tobey_r1
 * @CreateDate: 2021/3/21 15:42
 * @Description: java类作用描述
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/3/21 15:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WebViewLoading  extends CoordinatorLayout {

    Context context;
    ProgressBar progressBar;
    WebView webView;



    public WebViewLoading(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
     * 进度条颜色设置
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
        public void onGeolocationPermissionsHidePrompt() {
            super.onGeolocationPermissionsHidePrompt();
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
            super.onGeolocationPermissionsShowPrompt(origin, callback);
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

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        @SuppressLint("RestrictedApi")
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
    public void removeDiv(WebViewClient webViewClient,String divName){
        webViewClient.onLoadResource(webView,WebViewSettings.removeDiv(divName));
    }





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
          webView.loadUrl(url);
    }

    /**
     * 设置加载网址
     * @param url 链接
     */
    public void setUrl(Uri url){
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
