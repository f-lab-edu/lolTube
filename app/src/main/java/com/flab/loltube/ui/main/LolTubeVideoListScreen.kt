package com.flab.loltube.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.flab.domain.model.Video

@Composable
fun LolTubeVideoListRoute(
    modifier: Modifier = Modifier,
    viewModel: LolTubeViewModel = hiltViewModel()
) {
    val videos by viewModel.videos.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchVideos()
    }

    LolTubeVideoListScreen(
        videos = videos,
        modifier = modifier
    )
}

@Composable
fun LolTubeVideoListScreen(
    videos: List<Video>,
    modifier: Modifier = Modifier
) {
    VideoListContent(
        videos = videos,
        modifier = modifier
    )
}

@Composable
private fun VideoListContent(
    videos: List<Video>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(videos) { video ->
            VideoListItem(video = video)
        }
    }
}

@Composable
private fun VideoListItem(video: Video) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VideoThumbnail(
                thumbnailUrl = video.thumbnailUrl,
                title = video.title
            )

            Spacer(modifier = Modifier.width(8.dp))

            VideoInfo(
                title = video.title,
                channelTitle = video.channelTitle,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun VideoThumbnail(
    thumbnailUrl: String?,
    title: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = thumbnailUrl,
        contentDescription = title,
        modifier = modifier
            .size(120.dp, 90.dp)
            .clip(RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun VideoInfo(
    title: String,
    channelTitle: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = channelTitle,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}
