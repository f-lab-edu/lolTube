package com.flab.domain.model

data class Video(
    val videoId: String,
    val title: String,
    val channelTitle: String,
    val thumbnailUrl: String?
)

data class VideoSearchResult(
    val videos: List<Video>,
    val nextPageToken: String?,
    val hasMorePages: Boolean = nextPageToken != null
)
