package com.flab.shorts.webview

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

private const val TAG = "ShortsPlayer"

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ShortsPlayer(
    videoId: String,
    isActive: Boolean = true,
    isVideoReady: Boolean,
    webViewRef: WebView?,
    onWebViewCreated: (WebView) -> Unit,
    onPlayerStateChange: ((Int) -> Unit)? = null,
    onPlayerClicked: ((Boolean) -> Unit)? = null,
    onVideoReady: (() -> Unit)? = null
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(isActive, webViewRef) {
        webViewRef?.let { webView ->
            if (isActive) {
                Log.d(TAG, "Page activated, playing video: $videoId")
                webView.evaluateJavascript("if(window.player){ player.playVideo(); }", null)
            } else {
                Log.d(TAG, "Page deactivated, pausing video: $videoId")
                webView.evaluateJavascript(
                    "if(window.player){ player.pauseVideo(); player.mute(); }",
                    null
                )
            }
        }
    }

    LaunchedEffect(videoId, isActive, webViewRef) {
        if (!isActive) {
            webViewRef?.evaluateJavascript(
                "if(window.player){ player.pauseVideo(); player.stopVideo(); }",
                null
            )
        }
    }

    DisposableEffect(lifecycleOwner, webViewRef) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    if (isActive) {
                        Log.d(TAG, "App resumed, trying to resume video")
                        webViewRef?.evaluateJavascript(
                            "if(window.player && window.player.getPlayerState() === 2){ player.playVideo(); }",
                            null
                        )
                    }
                }

                Lifecycle.Event.ON_PAUSE -> {
                    Log.d(TAG, "App paused")
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            webViewRef?.let { webView ->
                webView.clearCache(true)
                webView.clearHistory()
                webView.removeAllViews()
                webView.destroy()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { context ->
                createShortsWebView(
                    context = context,
                    videoId = videoId,
                    onPlayerStateChange = onPlayerStateChange,
                    onPlayerClicked = onPlayerClicked,
                    onVideoReady = {
                        Log.d(TAG, "Video $videoId - ready, hiding loading")
                        onVideoReady?.invoke()
                    }
                ).also { webView ->
                    onWebViewCreated(webView)
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { webView ->
                onWebViewCreated(webView)
            }
        )

        if (!isVideoReady) {
            LoadingIndicator(withZIndex = true)
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
private fun createShortsWebView(
    context: android.content.Context,
    videoId: String,
    onPlayerStateChange: ((Int) -> Unit)?,
    onPlayerClicked: ((Boolean) -> Unit)?,
    onVideoReady: () -> Unit
): WebView {
    return WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        settings.javaScriptEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        settings.domStorageEnabled = true
        settings.allowContentAccess = true
        settings.allowFileAccess = true
        settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.cacheMode = android.webkit.WebSettings.LOAD_DEFAULT

        setLayerType(android.view.View.LAYER_TYPE_HARDWARE, null)

        addJavascriptInterface(
            WebViewInterface(
                onPlayerStateChange = { state ->
                    Log.d(TAG, "Video $videoId - Player state changed: $state")
                    onPlayerStateChange?.invoke(state)
                },
                onPlayerClicked = { isPlaying ->
                    Log.d(TAG, "Video $videoId - Player clicked, isPlaying: $isPlaying")
                    onPlayerClicked?.invoke(isPlaying)
                },
                onVideoReady = onVideoReady
            ),
            "AndroidInterface"
        )

        loadUrl("file:///android_asset/youtube_shorts.html?videoId=$videoId&mute=0")
    }
}
