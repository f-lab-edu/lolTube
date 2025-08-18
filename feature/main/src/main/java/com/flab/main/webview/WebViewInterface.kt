package com.flab.main.webview

import android.webkit.JavascriptInterface

class WebViewInterface(
    private val onVideoPlay: () -> Unit,
    private val onVideoPause: () -> Unit,
    private val onVideoEnd: () -> Unit
) {

    @JavascriptInterface
    fun onVideoPlay() {
        onVideoPlay.invoke()
    }

    @JavascriptInterface
    fun onVideoPause() {
        onVideoPause.invoke()
    }

    @JavascriptInterface
    fun onVideoEnd() {
        onVideoEnd.invoke()
    }
}
