package com.likexuan.checkin

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.app.Activity

/**
 * 极薄的 WebView 壳。
 *
 * 打卡程序的全部逻辑在 assets/ 里（就是那套 PWA 文件），
 * 这里只负责把它显示成一个全屏的原生 App。
 */
class MainActivity : Activity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this)
        setContentView(webView, ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ))

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true          // localStorage 靠这个，必须开
            databaseEnabled = true
            allowFileAccess = true
            cacheMode = WebSettings.LOAD_DEFAULT
        }

        // 禁止在 WebView 里横向滚动，页面本身是按手机屏设计的
        webView.isVerticalScrollBarEnabled = false
        webView.overScrollMode = View.OVER_SCROLL_NEVER

        // 站内链接留在 WebView，外部链接丢给系统浏览器
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView, request: android.webkit.WebResourceRequest
            ): Boolean {
                val url = request.url.toString()
                return if (url.startsWith("file:///android_asset")) {
                    false
                } else {
                    try {
                        startActivity(android.content.Intent(android.content.Intent.ACTION_VIEW, request.url))
                    } catch (_: Exception) {
                    }
                    true
                }
            }
        }

        // 深色模式下强制浅色，避免系统主题把背景染黑
        // forceDark 是 WebSettings 的属性（不是 WebView 的），FORCE_DARK_OFF 是 API 29 (Q) 引入
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            webView.settings.forceDark = WebSettings.FORCE_DARK_OFF
        }

        webView.loadUrl("file:///android_asset/index.html")
    }

    // 禁止左右滑动返回手势误触（页面内的横滑我们自己处理）
    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
