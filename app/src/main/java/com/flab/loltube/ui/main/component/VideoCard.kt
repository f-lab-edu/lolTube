package com.flab.loltube.ui.main.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.flab.domain.model.Video
import com.flab.loltube.ui.theme.LolTubeTheme

@Composable
fun VideoCard(
    video: Video,
    modifier: Modifier = Modifier,
    onClick: ((Video) -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { onClick?.invoke(video) }
    ) {
        VideoCardContent(video = video)
    }
}

@Composable
private fun VideoCardContent(
    video: Video,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        VideoThumbnail(
            thumbnailUrl = video.thumbnailUrl,
            contentDescription = video.title
        )

        Spacer(modifier = Modifier.width(12.dp))

        VideoDetails(
            video = video,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun VideoDetails(
    video: Video,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        VideoTitle(title = video.title)
        VideoChannelInfo(channelTitle = video.channelTitle)
    }
}

private val sampleVideo = Video(
    videoId = "1",
    title = "[LOL] 프레이 유나라 : 유나라는 이 맛이지~",
    channelTitle = "프레이 TV",
    thumbnailUrl = "https://i.ytimg.com/vi/TpFNF9chDHc/hqdefault.jpg"
)

@Preview(showBackground = true)
@Composable
private fun VideoCardPreview() {
    LolTubeTheme {
        VideoCard(
            video = sampleVideo,
            onClick = { }
        )
    }
}
