# webLoading
---------------------------
**WebView is the encapsulation of Marshal WebView and progress implementation, loading all kinds of web pages and the corresponding progress bar prompt**

# Quick Setup
**Add it in your root build.gradle at the end of repositories:**

```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
**Then add the dependency:**
```java
dependencies {
	        implementation 'com.github.Tobeyr1:webLoading:1.0.0'
	}
```
# Basic Usage
**Adding controls to a layout file**
```java
<com.tobey.webviewlibrary.WebViewLoading
        android:id="@+id/web"
        android:layout_height="match_parent"
        android:layout_width="match_parent" />
```
**Use**
```java
WebViewLoading webViewLoading =(WebViewLoading) findViewById(R.id.web);
//webview的基本配置（包括js开启、h5支持、https支持、加载进度条）
webViewLoading.setSettings(this);
//加载的url
webViewLoading.setUrl("https://blog.csdn.net/Tobey_r1");
//页面暂停
webViewLoading.setPause();
//页面恢复
webViewLoading.setResume();
//页面销毁
webViewLoading.setDestory();
```
**External methods**

| setProgressColor(Drawable drawable) | 可以设置对应加载进度条背景颜色、进度条颜色|
|--|--|
|  removeDiv(String divName)| 提供去网页广告等元素 方法|
|--|--|
|  goback(Activity activity)| 实现网页后退及返回到上一个页面方法 |
|--|--|
|  setCromeClient(WebChromeClient Client)| 提供自定义WebChromeClient方法|
|--|--|
|  setCromeClient(WebViewClient client)| 提高自定义WebViewClient方法 |
|--|--|
|  getWebView()| 获取当前webview实例|
|--|--|
|  getWebViewClient()| 获取当前WebViewClient方法 |
|--|--|
|  getWebChromeClient()| 获取当前WebChromeClient方法 |



详细使用效果等可以参考博文[WebLoading开源库，支持https访问](https://blog.csdn.net/Tobey_r1/article/details/115096272)
