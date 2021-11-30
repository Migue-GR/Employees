package com.employees.utils

import com.employees.R
import com.employees.model.enums.ErrorCode

class EmployeesException(val errorCode: ErrorCode = ErrorCode.UNKNOWN_ERROR) : Exception() {
    override fun getLocalizedMessage(): String = errorMessageFactory(errorCode)

    /**
     * @param errorCode Code describing the exception.
     * @return an error message by a given [ErrorCode].
     */
    private fun errorMessageFactory(errorCode: ErrorCode) =
        AppSession.provideApplicationContext().run {
            when (errorCode) {
                ErrorCode.UNKNOWN_ERROR -> getString(R.string.something_went_wrong)
                ErrorCode.SOMETHING_WENT_WRONG -> getString(R.string.something_went_wrong)
                ErrorCode.ERROR_INVALID_CREDENTIALS -> getString(R.string.invalid_credentials)
                ErrorCode.ERROR_IN_LOGOUT -> getString(R.string.error_in_logout)
                else -> getString(R.string.something_went_wrong)
            }
        }
}