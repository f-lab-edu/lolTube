package com.flab.data.service

import com.flab.data.BuildConfig
import com.flab.data.response.youtube.LolTubeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LolTubeService {

    @GET("search")
    suspend fun searchVideos(
        @Query("key") apiKey: String = BuildConfig.YOUTUBE_API_KEY,
        @Query("part") part: String = "snippet",
        @Query("q") query: String,
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 10,
        @Query("regionCode") regionCode: String = "KR"
    ): LolTubeSearchResponse
}
