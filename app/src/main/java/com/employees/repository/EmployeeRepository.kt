package com.employees.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.employees.datasource.local.AppDataBase
import com.employees.datasource.remote.EmployeesApi
import com.employees.model.local.Employee
import com.employees.model.remote.toEmployees
import timber.log.Timber

class EmployeeRepository(
    private val remoteDataSource: EmployeesApi,
    private val localDataSource: AppDataBase
) {
    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>> = _employees

    suspend fun getEmployees() {
        try {
            val response = remoteDataSource.getEmployees().employees?.filterNotNull() ?: listOf()
            val employees = response.toEmployees()
            localDataSource.withTransaction { localDataSource.employeesDao.updateItems(employees) }
            _employees.postValue(employees)
        } catch (e: Exception) {
            Timber.e(e, "Error while getting the employees")
            localDataSource.withTransaction {
                _employees.postValue(localDataSource.employeesDao.getItems())
            }
        }
    }
}