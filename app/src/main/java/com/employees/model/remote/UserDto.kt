package com.employees.model.remote

import com.employees.model.local.User

data class UserDto(
    val id: String?,
    val email: String?
)

fun UserDto.toUser() = User(id ?: "", email ?: "")