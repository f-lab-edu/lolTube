package com.flab.loltube.ui.main

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.flab.domain.model.Video
import com.flab.loltube.ui.main.component.VideoList
import com.flab.loltube.ui.main.component.EmptyVideoList
import com.flab.loltube.ui.theme.LolTubeTheme

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


private val sampleVideos = listOf(
    Video(
        videoId = "1",
        title = "[LOL] 프레이 유나라 : 유나라는 이 맛이지~",
        channelTitle = "프레이 TV",
        thumbnailUrl = "https://i.ytimg.com/vi/TpFNF9chDHc/hqdefault.jpg"
    ),
    Video(
        videoId = "2",
        title = "[LOL] 실버 탈출기",
        channelTitle = "게임 TV",
        thumbnailUrl = "https://i.ytimg.com/vi/example2/hqdefault.jpg"
    )
)

@Preview(showBackground = true)
@Composable
private fun LolTubeVideoListScreenPreview() {
    LolTubeTheme {
        LolTubeVideoListScreen(
            videos = sampleVideos,
            onVideoClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LolTubeVideoListScreenEmptyPreview() {
    LolTubeTheme {
        LolTubeVideoListScreen(
            videos = emptyList(),
            onVideoClick = { }
        )
    }
}
