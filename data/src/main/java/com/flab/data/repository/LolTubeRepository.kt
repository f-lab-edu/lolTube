package com.flab.data.repository

import com.flab.data.response.youtube.VideoItem
import com.flab.data.service.LolTubeService
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
            query = query,
            maxResults = maxResults,
            regionCode = regionCode
        )
        return response.items
    }
}
