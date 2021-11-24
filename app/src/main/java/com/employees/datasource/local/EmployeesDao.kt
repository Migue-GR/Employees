package com.employees.datasource.local

import androidx.room.*
import com.employees.model.local.Employee
import com.employees.model.local.User

@Dao
interface EmployeesDao {
    @Transaction
    suspend fun updateItems(items: List<Employee>) {
        clearTable()
        insertItems(items)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(item: List<Employee>)

    @Query("SELECT * FROM Employees")
    suspend fun getItems(): List<Employee>

    @Query("DELETE FROM Employees")
    fun clearTable()
}