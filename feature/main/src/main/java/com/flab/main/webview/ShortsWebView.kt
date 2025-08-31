package com.flab.main.webview

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

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
    ShortsPlayer(
        videoId = videoId,
        isActive = isActive,
        onPlayerStateChange = onPlayerStateChange,
        onPlayerClicked = onPlayerClicked,
        onVideoReady = onVideoReady
    )
}
