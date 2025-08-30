package com.flab.main.webview

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver


private const val TAG = "ShortsWebView"

@Composable
fun ShortsPager(
    videoIds: List<String>,
    onPageChanged: ((Int) -> Unit)? = null,
    onPlayerStateChange: ((String, Int) -> Unit)? = null,
    onPlayerClicked: ((String, Boolean) -> Unit)? = null,
    onVideoReady: ((String) -> Unit)? = null
) {
    val pagerState = rememberPagerState(pageCount = { videoIds.size })

    LaunchedEffect(pagerState.currentPage) {
        Log.d(
            TAG,
            "Pager moved to page: ${pagerState.currentPage}, videoId: ${videoIds[pagerState.currentPage]}"
        )
        onPageChanged?.invoke(pagerState.currentPage)
    }

    VerticalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        if (page == pagerState.currentPage) {
            val videoId = videoIds[page]

            ShortsWebView(
                videoId = videoId,
                isActive = true,
                onPlayerStateChange = { state ->
                    onPlayerStateChange?.invoke(videoId, state)
                },
                onPlayerClicked = { isPlaying ->
                    onPlayerClicked?.invoke(videoId, isPlaying)
                },
                onVideoReady = {
                    onVideoReady?.invoke(videoId)
                }
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ShortsWebView(
    videoId: String,
    isActive: Boolean = true,
    onPlayerStateChange: ((Int) -> Unit)? = null,
    onPlayerClicked: ((Boolean) -> Unit)? = null,
    onVideoReady: (() -> Unit)? = null
) {
    var isVideoReady by remember { mutableStateOf(false) }
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    LaunchedEffect(isActive) {
        if (isActive) {
            Log.d(TAG, "Page activated, playing video: $videoId")
            webViewRef?.evaluateJavascript("if(window.player){ player.playVideo(); }", null)
        } else {
            Log.d(TAG, "Page deactivated, pausing video: $videoId")
            webViewRef?.evaluateJavascript(
                "if(window.player){ player.pauseVideo(); player.mute(); }",
                null
            )
        }
    }

    LaunchedEffect(videoId, isActive) {
        if (!isActive) {
            webViewRef?.evaluateJavascript(
                "if(window.player){ player.pauseVideo(); player.stopVideo(); }",
                null
            )
        }
    }

    DisposableEffect(lifecycleOwner) {
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
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )

                    settings.javaScriptEnabled = true
                    settings.mediaPlaybackRequiresUserGesture = false

                    settings.domStorageEnabled = true
                    settings.allowContentAccess = true
                    settings.allowFileAccess = true
                    settings.mixedContentMode =
                        android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
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
                            onVideoReady = {
                                Log.d(TAG, "Video $videoId - ready, hiding loading")
                                isVideoReady = true
                                onVideoReady?.invoke()
                            }
                        ),
                        "AndroidInterface"
                    )

                    loadUrl("file:///android_asset/youtube_shorts.html?videoId=$videoId&mute=0")
                    webViewRef = this
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { webViewRef = it }
        )

        if (!isVideoReady) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .zIndex(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
