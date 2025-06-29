package com.flab.loltube.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.domain.model.user.User
import com.flab.domain.usecase.UsersUseCase
import kotlinx.coroutines.launch

class UserViewModel(private val usersUseCase: UsersUseCase) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            val users = usersUseCase(10)
            _users.value = users
        }
    }
}
