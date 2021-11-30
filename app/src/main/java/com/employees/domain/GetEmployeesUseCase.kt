package com.employees.domain

import com.employees.repository.EmployeeRepository
import com.employees.utils.ext.useCaseExecution
import kotlinx.coroutines.Dispatchers

class GetEmployeesUseCase(private val repository: EmployeeRepository) {
    suspend operator fun invoke() = useCaseExecution(Dispatchers.IO) {
        repository.getEmployees()
    }
}