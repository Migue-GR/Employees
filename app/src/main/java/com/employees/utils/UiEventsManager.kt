package com.employees.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.employees.model.enums.Errors
import com.employees.model.enums.Success

object UiEventsManager {
    var shouldUpdateEmployees = true

    private val mError = MutableLiveData<Errors>()
    val error = mError as LiveData<Errors>
    fun showError(error: Errors) = mError.postValue(error)

    private val mSuccess = MutableLiveData<Success>()
    val success = mSuccess as LiveData<Success>
    fun showSuccess(success: Success) = mSuccess.postValue(success)

    private val mShowLoading = MutableLiveData<Boolean>()
    val showLoading = mShowLoading as LiveData<Boolean>
    fun showLoading(showLoading: Boolean) = mShowLoading.postValue(showLoading)
}