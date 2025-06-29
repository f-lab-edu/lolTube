package com.flab.domain.repository

import com.flab.domain.model.user.User

interface UserRepository {
    suspend fun getUsers(count: Int): List<User>
}
