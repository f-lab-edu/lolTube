package com.flab.loltube.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flab.data.repository.UserRepository
import com.flab.loltube.model.user.UserUiState
import com.flab.loltube.model.user.mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _users = MutableStateFlow<List<UserUiState>>(emptyList())
    val users = _users.asStateFlow()

    fun fetchUsers(count: Int) {
        viewModelScope.launch {
            val users = userRepository.getUsers(count)
            _users.value = users.map { it.mapper() }
        }
    }
}
