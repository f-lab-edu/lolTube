package com.flab.main.webview

import android.webkit.JavascriptInterface

class WebViewInterface(
    private val onPlayerStateChange: (Int) -> Unit = {},
    private val onPlayerClicked: (Boolean) -> Unit = {},
    private val onVideoReady: () -> Unit = {}
) {

    @JavascriptInterface
    fun onPlayerStateChange(state: Int) {
        onPlayerStateChange.invoke(state)
    }

    @JavascriptInterface
    fun onPlayerClicked(isPlaying: Boolean) {
        onPlayerClicked.invoke(isPlaying)
    }

    @JavascriptInterface
    fun onVideoReady() {
        onVideoReady.invoke()
    }
}
