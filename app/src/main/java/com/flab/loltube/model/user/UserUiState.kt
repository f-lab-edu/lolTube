package com.flab.loltube.model.user

import com.flab.data.model.user.User

data class UserUiState(
    val gender: String,
    val email: String,
    val phone: String,
    val cell: String
)

fun User.mapper() = UserUiState(
    gender = gender,
    email = email,
    phone = phone,
    cell = cell
)
