package com.flab.data.service

import com.flab.data.model.user.UserResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {

    @GET("api")
    suspend fun getUsers(
        @Query("results") count: Int
    ): UserResponseDto
}
