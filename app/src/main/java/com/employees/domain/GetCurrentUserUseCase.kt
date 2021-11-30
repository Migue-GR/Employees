package com.employees.domain

import com.employees.repository.UserRepository
import com.employees.utils.ext.useCaseExecution
import kotlinx.coroutines.Dispatchers

class GetCurrentUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke() = useCaseExecution(Dispatchers.IO) {
        repository.getCurrentUser()
    }
}