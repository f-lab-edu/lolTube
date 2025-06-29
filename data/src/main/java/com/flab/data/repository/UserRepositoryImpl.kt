package com.flab.data.repository

import com.flab.data.service.RandomUserService
import com.flab.domain.model.user.User
import com.flab.domain.repository.UserRepository

class UserRepositoryImpl(
    private val randomUserService: RandomUserService
) : UserRepository {

    override suspend fun getUsers(count: Int): List<User> {
        val response = randomUserService.getUsers(count)

        return response.results.map { userDto ->
            User(
                gender = userDto.gender,
                email = userDto.email,
                phone = userDto.phone,
                cell = userDto.cell
            )
        }
    }
}
