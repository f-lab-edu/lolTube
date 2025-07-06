package com.flab.loltube.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.data.repository.UserRepository
import com.flab.loltube.model.user.UserUiState
import com.flab.loltube.model.user.mapper
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _users = MutableLiveData<List<UserUiState>>()
    val users: LiveData<List<UserUiState>> = _users

    suspend fun fetchUsers(count: Int) {
        viewModelScope.launch {
            val users = userRepository.getUsers(count)
            _users.value = users.map { it.mapper() }
        }
    }
}
