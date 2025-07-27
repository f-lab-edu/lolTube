package com.flab.network.response.youtube

import com.google.gson.annotations.SerializedName

data class LolTubeSearchResponse(
    @SerializedName("items") val items: List<VideoItem>
)

data class VideoItem(
    @SerializedName("id") val id: VideoId,
    @SerializedName("snippet") val snippet: VideoSnippet
)

data class VideoId(
    @SerializedName("videoId") val videoId: String?
)

data class VideoSnippet(
    @SerializedName("title") val title: String,
    @SerializedName("channelTitle") val channelTitle: String,
    @SerializedName("thumbnails") val thumbnails: Thumbnails
)

data class Thumbnails(
    @SerializedName("high") val high: ThumbnailDetail?
)

data class ThumbnailDetail(
    @SerializedName("url") val url: String
)
