package com.flab.data.mapper

import com.flab.domain.model.Video
import com.flab.network.response.youtube.VideoItem

fun VideoItem.toDomain(): Video = Video(
    videoId = id.videoId ?: "",
    title = snippet.title,
    channelTitle = snippet.channelTitle,
    thumbnailUrl = snippet.thumbnails.high?.url
)
