package com.flab.network.service

import com.flab.network.response.youtube.LolTubeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LolTubeService {

    @GET("search")
    suspend fun searchVideos(
        @Query("key") apiKey: String,
        @Query("part") part: String = "snippet",
        @Query("q") query: String,
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 10,
        @Query("regionCode") regionCode: String = "KR"
    ): LolTubeSearchResponse
}
