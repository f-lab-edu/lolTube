package com.flab.domain.usecase

import com.flab.domain.model.Video
import com.flab.domain.model.VideoSearchResult
import com.flab.domain.repository.VideoRepository
import javax.inject.Inject

class SearchVideosUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    suspend operator fun invoke(
        query: String,
        maxResults: Int = 10,
        regionCode: String = "KR"
    ): List<Video> = videoRepository.searchVideos(query, maxResults, regionCode)

    suspend fun searchWithPaging(
        query: String,
        maxResults: Int = 10,
        regionCode: String = "KR",
        pageToken: String? = null
    ): VideoSearchResult = videoRepository.searchVideosWithPaging(
        query = query,
        maxResults = maxResults,
        regionCode = regionCode,
        pageToken = pageToken
    )
}
