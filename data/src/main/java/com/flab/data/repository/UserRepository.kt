package com.flab.data.repository

import com.flab.data.model.user.User

interface UserRepository {
    suspend fun getUsers(count: Int): List<User>
}
