package com.employees.repository

import androidx.room.withTransaction
import com.employees.datasource.local.AppDataBase
import com.employees.datasource.remote.EmployeesApi
import com.employees.model.local.Employee
import com.employees.model.remote.toEmployees

class EmployeeRepository(
    private val remoteDataSource: EmployeesApi,
    private val localDataSource: AppDataBase
) {
    suspend fun getEmployees(): List<Employee> {
        val response = remoteDataSource.getEmployees().employees?.filterNotNull() ?: listOf()
        val employees = response.toEmployees()
        localDataSource.withTransaction { localDataSource.employeesDao.updateItems(employees) }
        return employees
    }
}