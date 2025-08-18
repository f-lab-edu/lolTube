package com.flab.main.webview

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ShortsWebView() {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }
    var webView: WebView? by remember { mutableStateOf(null) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                WebView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                        }

                        @Deprecated("Deprecated in Java", ReplaceWith("false"))
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            url: String?
                        ): Boolean {
                            return false
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onConsoleMessage(consoleMessage: android.webkit.ConsoleMessage?): Boolean {
                            return super.onConsoleMessage(consoleMessage)
                        }

                        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                            super.onShowCustomView(view, callback)
                        }

                        override fun onHideCustomView() {
                            super.onHideCustomView()
                        }
                    }

                    settings.apply {
                        javaScriptEnabled = true
                        javaScriptCanOpenWindowsAutomatically = true

                        domStorageEnabled = true
                        databaseEnabled = true

                        allowFileAccess = true
                        allowContentAccess = true
                        allowUniversalAccessFromFileURLs = true
                        allowFileAccessFromFileURLs = true

                        mediaPlaybackRequiresUserGesture = false

                        mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

                        cacheMode = android.webkit.WebSettings.LOAD_DEFAULT

                        useWideViewPort = true
                        loadWithOverviewMode = true

                        userAgentString += " ShortsWebView/1.0"

                        setSupportZoom(false)
                        builtInZoomControls = false
                        displayZoomControls = false

                        setSupportMultipleWindows(false)
                        setGeolocationEnabled(false)
                    }

                    setLayerType(View.LAYER_TYPE_HARDWARE, null)

                    setBackgroundColor(0x00000000)

                    addJavascriptInterface(
                        WebViewInterface(
                            onVideoPlay = {
                                isPlaying = true
                            },
                            onVideoPause = {
                                isPlaying = false
                            },
                            onVideoEnd = {
                                isPlaying = false
                            }
                        ),
                        "AndroidInterface"
                    )

                    loadUrl("file:///android_asset/youtube_shorts.html")
                    webView = this
                }
            },
            update = { view ->
                view.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            webView?.apply {
                clearHistory()
                clearCache(true)
                loadUrl("about:blank")
                onPause()
                removeAllViews()
                destroy()
            }
        }
    }
}

fun playVideo(webView: WebView?) {
    webView?.evaluateJavascript("playVideo()", null)
}

fun pauseVideo(webView: WebView?) {
    webView?.evaluateJavascript("pauseVideo()", null)
}

fun toggleVideo(webView: WebView?) {
    webView?.evaluateJavascript("toggleVideo()", null)
}

fun setVideoState(webView: WebView?, playing: Boolean) {
    webView?.evaluateJavascript("setVideoState($playing)", null)
}
