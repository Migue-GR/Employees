package com.employees.domain

import com.employees.model.enums.ErrorCode.ERROR_IN_LOGOUT
import com.employees.repository.UserRepository
import com.employees.utils.EmployeesException
import com.employees.utils.ext.useCaseExecution
import kotlinx.coroutines.Dispatchers

class LogoutUseCase(private val repository: UserRepository) {
    suspend operator fun invoke() = useCaseExecution(Dispatchers.IO) {
        if (repository.logout()) {
            true
        } else {
            throw EmployeesException(ERROR_IN_LOGOUT)
        }
    }
}