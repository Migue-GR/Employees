package com.employees.domain

import com.employees.repository.UserRepository
import com.employees.utils.ext.useCaseExecution
import kotlinx.coroutines.Dispatchers

class LoginUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = useCaseExecution(Dispatchers.IO) {
        repository.login(email, password)
    }
}