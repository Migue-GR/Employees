package com.employees.utils

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object AppSession {
    var shouldUpdateEmployees = true
    private var applicationContext: Application? = null
    private const val INITIALIZATION_ERROR_EXCEPTION_MESSAGE =
        "Be sure to call AppSession.initialize within your Application class onCreate function."

    fun initialize(appCtx: Application) = run { applicationContext = appCtx }

    fun provideApplicationContext() = applicationContext ?: run {
        throw Exception(INITIALIZATION_ERROR_EXCEPTION_MESSAGE)
    }

    private val mGlobalError = MutableLiveData<EmployeesException>()
    val globalError = mGlobalError as LiveData<EmployeesException>
    fun showGlobalError(e: EmployeesException) = mGlobalError.postValue(e)

    private val mGlobalProgressBar = MutableLiveData<Boolean>()
    val globalProgressBar = mGlobalProgressBar as LiveData<Boolean>
    fun showGlobalProgressBar(showLoading: Boolean) = mGlobalProgressBar.postValue(showLoading)
}