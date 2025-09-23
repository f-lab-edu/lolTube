package com.flab.domain.repository

import com.flab.domain.model.Video
import com.flab.domain.model.VideoSearchResult

interface VideoRepository {

    suspend fun searchVideos(
        query: String,
        maxResults: Int = 10,
        regionCode: String = "KR"
    ): List<Video>

    suspend fun searchVideosWithPaging(
        query: String,
        maxResults: Int = 10,
        regionCode: String = "KR",
        pageToken: String? = null
    ): VideoSearchResult
}
