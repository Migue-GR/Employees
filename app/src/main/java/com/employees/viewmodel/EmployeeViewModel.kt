package com.employees.viewmodel

import androidx.lifecycle.ViewModel
import com.employees.repository.EmployeeRepository
import com.employees.utils.UiEventsManager.shouldUpdateEmployees
import com.employees.utils.ext.launchUseCase

class EmployeeViewModel(private val repository: EmployeeRepository) : ViewModel() {
    val employees = repository.employees

    fun getEmployees() = launchUseCase {
        if (shouldUpdateEmployees) {
            shouldUpdateEmployees = false
            repository.getEmployees()
        }
    }
}