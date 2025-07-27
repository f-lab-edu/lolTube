package com.flab.domain.repository

import com.flab.domain.model.Video

interface VideoRepository {

    suspend fun searchVideos(
        query: String,
        maxResults: Int = 10,
        regionCode: String = "KR"
    ): List<Video>
}
