package com.employees.viewmodel

import androidx.lifecycle.ViewModel
import com.employees.repository.UserRepository
import com.employees.utils.ext.launchUseCase

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    val user = repository.user

    fun login(email: String, password: String) = launchUseCase {
        repository.login(email, password)
    }

    fun getCurrentUser() = launchUseCase {
        repository.getCurrentUser()
    }

    fun logout() = launchUseCase {
        repository.logout()
    }
}