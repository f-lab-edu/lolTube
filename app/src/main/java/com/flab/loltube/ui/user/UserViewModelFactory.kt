package com.flab.loltube.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flab.data.repository.UserRepositoryImpl
import com.flab.data.service.RetrofitClient
import com.flab.domain.usecase.UsersUseCase

class UserViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {

            val randomUserService = RetrofitClient.randomUserService
            val userRepository = UserRepositoryImpl(randomUserService)
            val usersUseCase = UsersUseCase(userRepository)

            @Suppress("UNCHECKED_CAST")
            return UserViewModel(usersUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
