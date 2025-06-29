package com.flab.domain.usecase

import com.flab.domain.model.user.User
import com.flab.domain.repository.UserRepository

class UsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(count: Int): List<User> {
        return userRepository.getUsers(count)
    }
}

