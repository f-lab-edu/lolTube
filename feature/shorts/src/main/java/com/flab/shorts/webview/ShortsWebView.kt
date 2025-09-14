package com.flab.shorts.webview

import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flab.shorts.ShortsViewModel
import androidx.hilt.navigation.compose.hiltViewModel as hiltViewModel1

private const val TAG = "ShortsWebView"

@Composable
fun ShortsRoute(
    viewModel: ShortsViewModel = hiltViewModel1()
) {
    val shortsVideos by viewModel.shortsVideoIds.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (shortsVideos.isEmpty()) {
            viewModel.fetchShortsVideos()
        }
    }

    val videoIds = shortsVideos.map { it.videoId }

    if (videoIds.isNotEmpty()) {
        ShortsPager(
            videoIds = videoIds
        )
    }
}

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
            LoadingIndicator()
        }
    }
}

@Composable
fun ShortsWebView(
    videoId: String,
    isActive: Boolean = true,
    onPlayerStateChange: ((Int) -> Unit)? = null,
    onPlayerClicked: ((Boolean) -> Unit)? = null,
    onVideoReady: (() -> Unit)? = null
) {
    var isVideoReady by remember(videoId) { mutableStateOf(false) }
    var webViewRef by remember(videoId) { mutableStateOf<WebView?>(null) }
    var isWebViewDestroyed by remember(videoId) { mutableStateOf(false) }

    DisposableEffect(videoId) {
        onDispose {
            isWebViewDestroyed = true
            webViewRef?.let { webView ->
                try {
                    webView.stopLoading()
                    webView.clearCache(true)
                    webView.clearHistory()
                    webView.removeAllViews()
                    (webView.parent as? android.view.ViewGroup)?.removeView(webView)
                    webView.destroy()
                } catch (e: Exception) {
                    Log.w(TAG, "Error destroying WebView: ${e.message}")
                }
                webViewRef = null
            }
        }
    }

    ShortsPlayer(
        videoId = videoId,
        isActive = isActive,
        isVideoReady = isVideoReady,
        webViewRef = if (isWebViewDestroyed) null else webViewRef,
        onWebViewCreated = { webView ->
            if (!isWebViewDestroyed) {
                webViewRef = webView
            }
        },
        onPlayerStateChange = onPlayerStateChange,
        onPlayerClicked = onPlayerClicked,
        onVideoReady = {
            if (!isWebViewDestroyed) {
                isVideoReady = true
                onVideoReady?.invoke()
            }
        }
    )
}
