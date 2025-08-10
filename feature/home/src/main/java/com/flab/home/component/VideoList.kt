package com.flab.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flab.designsystem.theme.LolTubeTheme
import com.flab.domain.model.Video

@Composable
fun VideoList(
    videos: List<Video>,
    modifier: Modifier = Modifier,
    onVideoClick: (Video) -> Unit = {}
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
    ),
    Video(
        videoId = "3",
        title = "[LOL] 챔피언 가이드",
        channelTitle = "리그 오브 레전드",
        thumbnailUrl = "https://i.ytimg.com/vi/example3/hqdefault.jpg"
    )
)

@Preview(showBackground = true)
@Composable
private fun VideoListPreview() {
    LolTubeTheme {
        VideoList(
            videos = sampleVideos,
            onVideoClick = { }
        )
    }
}
