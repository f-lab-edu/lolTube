package com.flab.loltube.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flab.domain.model.Video
import com.flab.loltube.R
import com.flab.loltube.ui.main.component.VideoCard
import androidx.core.net.toUri

@Composable
fun LolTubeVideoListRoute(
    modifier: Modifier = Modifier,
    viewModel: LolTubeViewModel = hiltViewModel()
) {
    val videos by viewModel.videos.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchVideos()
    }

    LolTubeVideoListScreen(
        videos = videos,
        modifier = modifier,
        onVideoClick = { video ->
            openYouTubeVideo(context, video.videoId)
        }
    )
}

private fun openYouTubeVideo(context: Context, videoId: String) {
    try {
        val youtubeIntent = Intent(Intent.ACTION_VIEW, "vnd.youtube:$videoId".toUri())
        youtubeIntent.setPackage("com.google.android.youtube")
        context.startActivity(youtubeIntent)
    } catch (e: Exception) {
        val webIntent =
            Intent(Intent.ACTION_VIEW, "https://www.youtube.com/watch?v=$videoId".toUri())
        context.startActivity(webIntent)
    }
}

@Composable
fun LolTubeVideoListScreen(
    videos: List<Video>,
    modifier: Modifier = Modifier,
    onVideoClick: (Video) -> Unit = {}
) {
    if (videos.isEmpty()) {
        EmptyVideoList(modifier = modifier)
    } else {
        VideoList(
            videos = videos,
            modifier = modifier,
            onVideoClick = onVideoClick
        )
    }
}

@Composable
fun EmptyVideoList(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.empty_video_list)
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun VideoList(
    videos: List<Video>,
    modifier: Modifier = Modifier,
    onVideoClick: (Video) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = videos,
            key = { video -> video.videoId }
        ) { video ->
            VideoCard(
                video = video,
                onClick = { onVideoClick(video) }
            )
        }
    }
}
