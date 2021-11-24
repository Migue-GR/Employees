package com.employees.model.remote

import com.employees.model.local.Employee

data class EmployeesResponse(
    val employees: List<EmployeeDto?>?
)

data class EmployeeDto(
    val id: Int?,
    val firstName: String?,
    val lastName: String?,
    val image: String?,
    val description: String?,
    val rating: Float?
)

fun List<EmployeeDto>.toEmployees(): List<Employee> {
    val employees = mutableListOf<Employee>()
    for (item in this) {
        employees.add(
            Employee(
                item.id ?: 0,
                item.firstName ?: "",
                item.lastName ?: "",
                item.image ?: "",
                item.description ?: "",
                item.rating ?: 0.0f
            )
        )
    }
    return employees
}