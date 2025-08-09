package com.flab.data.repository

import com.flab.domain.model.Video
import com.flab.domain.repository.VideoRepository
import com.flab.data.mapper.toDomain
import com.flab.network.service.LolTubeService
import com.flab.data.BuildConfig
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val lolTubeService: LolTubeService
) : VideoRepository {
    override suspend fun searchVideos(
        query: String,
        maxResults: Int,
        regionCode: String
    ): List<Video> {
        val response = lolTubeService.searchVideos(
            apiKey = BuildConfig.YOUTUBE_API_KEY,
            query = query,
            maxResults = maxResults,
            regionCode = regionCode
        )
        return response.items.map { it.toDomain() }
    }
}
