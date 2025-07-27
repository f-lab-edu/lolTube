package com.flab.data.repository

import com.flab.network.response.youtube.VideoItem
import com.flab.network.service.LolTubeService
import com.flab.data.BuildConfig
import javax.inject.Inject

interface LolTubeRepository {
    suspend fun searchVideos(query: String, maxResults: Int, regionCode: String): List<VideoItem>
}

class LolTubeRepositoryImpl @Inject constructor(
    private val lolTubeService: LolTubeService
) : LolTubeRepository {

    override suspend fun searchVideos(
        query: String,
        maxResults: Int,
        regionCode: String
    ): List<VideoItem> {
        val response = lolTubeService.searchVideos(
            apiKey = BuildConfig.YOUTUBE_API_KEY,
            query = query,
            maxResults = maxResults,
            regionCode = regionCode
        )
        return response.items
    }
}
