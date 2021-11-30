package com.employees.viewmodel

import androidx.lifecycle.ViewModel
import com.employees.domain.LoginUseCase
import com.employees.utils.ext.resultLiveData

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    fun login(email: String, password: String) = resultLiveData {
        loginUseCase(email, password)
    }
}