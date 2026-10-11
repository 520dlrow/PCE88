package com.pce.mauritanie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        webView = WebView(this).apply {
            overScrollMode = View.OVER_SCROLL_NEVER
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = true
            settings.allowContentAccess = true
            settings.allowUniversalAccessFromFileURLs = true
            settings.allowFileAccessFromFileURLs = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.mediaPlaybackRequiresUserGesture = false

            // Footer links (official source, mail, WhatsApp) open in system apps.
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    val url = request?.url?.toString() ?: return false
                    if (url.startsWith("http://") ||
                        url.startsWith("https://") ||
                        url.startsWith("mailto:") ||
                        url.startsWith("tel:")) {
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                        } catch (_: Exception) {}
                        return true
                    }
                    return false
                }
            }

            loadUrl("file:///android_asset/index.html")
        }

        setContentView(webView)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()
    }
}
