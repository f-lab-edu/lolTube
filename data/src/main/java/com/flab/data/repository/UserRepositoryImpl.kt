package com.flab.data.repository

import com.flab.data.model.user.User
import com.flab.data.service.RandomUserService

class UserRepositoryImpl(
    private val randomUserService: RandomUserService
) : UserRepository {
    override suspend fun getUsers(count: Int): List<User> {
        val response = randomUserService.getUsers(count)

        return response.results
    }
}
