package com.employees.viewmodel

import androidx.lifecycle.ViewModel
import com.employees.domain.GetCurrentUserUseCase
import com.employees.utils.ext.resultLiveData

class SplashViewModel(private val getCurrentUserUseCase: GetCurrentUserUseCase) : ViewModel() {
    fun getCurrentUser() = resultLiveData {
        getCurrentUserUseCase()
    }
}