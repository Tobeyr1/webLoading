package com.tobery.weblib

import com.tobery.weblib.WebViewSettings.setWebSettings
import com.tobery.weblib.WebViewSettings.onDestroy
import android.widget.RelativeLayout
import android.view.LayoutInflater
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.webkit.WebView.WebViewTransport
import android.net.http.SslError
import android.os.Message
import android.util.AttributeSet
import android.webkit.*
import android.widget.ProgressBar
import java.util.ArrayList

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
class WebViewLoading : RelativeLayout {

    private var mContext: Context? = null
    var progressBar: ProgressBar? = null

    /**
     * 获取当前webview实例
     */
    var webView: WebView? = null

    /**
     * 获取网页标题  */
    var webTitle : getHtmlTitle? = null


    constructor(context: Context) : super(context) {
        this.mContext = context
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.mContext = context
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.mContext = context
        initView(context)
    }

    private fun initView(context: Context) {
        val inflate = LayoutInflater.from(context).inflate(R.layout.layout_webprogress, null)
        progressBar = inflate.findViewById(R.id.progressBar)
        webView = inflate.findViewById(R.id.webView)
        addView(inflate)
    }

    /**
     * 基本设置
     * @param activity 活动
     */
    fun setSettings(activity: Activity?) {
        setWebSettings(webView!!, activity!!)
        intClient()
    }

    /**
     * 进度条加载颜色设置
     * @param drawable 渲染
     */
    fun setProgressColor(drawable: Drawable?) {
        progressBar!!.progressDrawable = drawable
    }

    private fun intClient() {
        webView!!.webChromeClient = webChromeClient
        webView!!.webViewClient = webViewClient
    }

    /**
     * 获取当前WebChromeClient实例
     */
    private val webChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100) {
                progressBar!!.visibility = GONE //加载完网页进度条消失
                webView!!.visibility = VISIBLE
            } else {
                webView!!.visibility = GONE
                progressBar!!.visibility = VISIBLE //开始加载网页时显示进度条
                progressBar!!.progress = newProgress //设置进度值
            }
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            webTitle?.invoke(title)
        }

        override fun onShowFileChooser(
            webView: WebView,
            filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            return true
        }

        override fun onCreateWindow(
            view: WebView,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message
        ): Boolean {
            val transport = resultMsg.obj as WebViewTransport
            transport.webView = view
            resultMsg.sendToTarget()
            return true
        }
    }


    /**
     * 获取当前webviewclient实例
     */
    private val webViewClient: WebViewClient = object : WebViewClient() {
        override fun onLoadResource(view: WebView, url: String) {
            super.onLoadResource(view, url)
            if (javascript != "") {
                view.loadUrl(javascript)
            }
        }

        /**
         * 多页面在同一个WebView中打开，就是不新建activity或者调用系统浏览器打开
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        //解决证书问题
        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            handler.proceed()
        }
    }

    /**
     * 提供页面返回方法
     * @param  activity 活动
     */
    fun goBack(activity: Activity) {
        if (webView!!.canGoBack() && webView!!.url != currentUrl) {
            webView!!.goBack()
        } else {
            activity.finish()
        }
    }

    /**
     * 提供自定义WebChromeClient接口
     * @param  webChromeClient 回调
     */
    fun setChromeClient(webChromeClient: WebChromeClient?) {
        webView!!.webChromeClient = webChromeClient
    }

    /**
     * 提供自定义WebViewClient接口
     * @param  client 回调
     */
    fun setChromeClient(client: WebViewClient?) {
        webView!!.webViewClient = client!!
    }

    /**
     * 去除网页元素
     * @param divName 要去除的元素id
     */
    fun removeDiv(divName: String) {
        javascript = "javascript:function removeDiv() {" +
                "var divs = document.getElementsByClassName('" + divName + "');" +
                "var firstdiv = divs[0];" +
                "firstdiv.remove();" + "}" +
                "javascript:removeDiv();"
    }

    fun getTitle(title: getHtmlTitle){
        this.webTitle = title
    }

    /**
     * 页面销毁时调用此方法
     */
    fun setDestroy() {
        onDestroy(webView)
    }

    /**
     * 页面恢复时调用
     */
    fun setResume() {
        webView!!.onResume()
        webView!!.resumeTimers()
    }

    /**
     * 父布局暂停时调用此方法
     */
    fun setPause() {
        webView!!.onPause()
        webView!!.pauseTimers() //小心这个！！！暂停整个 WebView 所有布局、解析、JS。
    }

    /**
     * 设置加载网址
     * @param url 链接
     */
    fun setUrl(url: String?) {
        currentUrl = url
        webView!!.loadUrl(url!!)
    }

    /**
     * 设置加载网址
     * @param url 链接
     */
    fun setUrl(url: Uri) {
        currentUrl = url.toString()
        webView!!.loadUrl(url.toString())
    }

    companion object {
        private var currentUrl: String? = null
        private var javascript = ""
        private val js = ArrayList<String>()
    }
}
typealias getHtmlTitle = (webTitle: String) -> Unit