package com.employees.datasource.remote

import com.employees.model.remote.EmployeesResponse
import retrofit2.http.GET

interface EmployeesApi {
    @GET(".")
    suspend fun getEmployees(): EmployeesResponse
}