package com.employees.viewmodel

import androidx.lifecycle.ViewModel
import com.employees.domain.GetEmployeesUseCase
import com.employees.domain.LogoutUseCase
import com.employees.utils.ext.resultLiveData

class HomeViewModel(
    private val getEmployeesUseCase: GetEmployeesUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    fun getEmployees() = resultLiveData {
        getEmployeesUseCase()
    }

    fun logout() = resultLiveData {
        logoutUseCase()
    }
}