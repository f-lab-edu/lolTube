package com.flab.data.repository

import com.flab.data.model.user.User
import com.flab.data.service.RandomUserService
import com.flab.data.service.RetrofitClient

class UserRepositoryImpl(
    private val randomUserService: RandomUserService = RetrofitClient.createService()
) : UserRepository {
    override suspend fun getUsers(count: Int): List<User> {
        val response = randomUserService.getUsers(count)

        return response.results
    }
}
