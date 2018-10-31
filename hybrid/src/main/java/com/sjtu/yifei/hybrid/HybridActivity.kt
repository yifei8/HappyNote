package com.sjtu.yifei.hybrid

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.webkit.*
import com.sjtu.yifei.annotation.Route
import com.sjtu.yifei.base.BaseActivity
import com.sjtu.yifei.base.util.LogUtil
import com.sjtu.yifei.base.util.NetworkUtil
import com.sjtu.yifei.base.util.setupActionBar
import com.sjtu.yifei.hybrid.web.BridgeWebViewClient
import com.sjtu.yifei.router.RouterPath
import kotlinx.android.synthetic.main.hybrid_activity.*

@Route(path = RouterPath.LAUNCHER_HYBRID)
class HybridActivity : BaseActivity() {

    companion object {
        const val TAG = "HybridFragment"
        const val EXTRA_SEARCH_KEY = "hybrid_load_url"
    }
    private var mUrl:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.hybrid_activity)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        mUrl = intent.getStringExtra(EXTRA_SEARCH_KEY)
        initWebSettings()

        initWebViewClient()

        initWebChromeClient()

        if (!TextUtils.isEmpty(mUrl)) {
            webView.loadUrl(mUrl)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebSettings() {

        val settings = webView.settings
        //支持JS
        settings.javaScriptEnabled = true
        //支持插件
        settings.pluginState = WebSettings.PluginState.ON
        //设置适应屏幕
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //支持缩放
        settings.setSupportZoom(false)
        //隐藏原生的缩放控件
        settings.displayZoomControls = false
        //支持内容重新布局
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        settings.supportMultipleWindows()
        settings.setSupportMultipleWindows(true)
        //设置缓存模式
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCacheEnabled(true)

        settings.setAppCachePath(webView.context.cacheDir.absolutePath)

        //设置可访问文件
        settings.allowFileAccess = true

        //支持获取手势焦点
        webView.requestFocusFromTouch()
        //当webview调用requestFocus时为webview设置节点
        settings.setNeedInitialFocus(true)
        //支持自动加载图片
        settings.loadsImagesAutomatically = Build.VERSION.SDK_INT >= 19
        settings.setNeedInitialFocus(true)
        //设置编码格式
        settings.defaultTextEncodingName = "UTF-8"

        if (NetworkUtil.isNetworkAvailable(this.applicationContext)) {
            settings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            settings.cacheMode = WebSettings.LOAD_CACHE_ONLY
        }

        LogUtil.e(TAG, "webView.context.cacheDir.absolutePath:" + webView.context.cacheDir.absolutePath)
    }

    private fun initWebViewClient() {
        webView.webViewClient = object : BridgeWebViewClient(webView) {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                //todo
            }

            override fun onCustomPageFinished(view: WebView?, url: String?) {
                super.onCustomPageFinished(view, url)
            }

            override fun onCustomShouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
            }

            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                super.onReceivedHttpError(view, request, errorResponse)
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                super.onReceivedSslError(view, handler, error)
            }
        }
    }

    private fun initWebChromeClient() {
        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                LogUtil.d(TAG, "newProgress -> $newProgress")
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
                setTitle(title)
            }

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
