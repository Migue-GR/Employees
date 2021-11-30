package com.employees.repository

import androidx.room.withTransaction
import com.employees.datasource.local.AppDataBase
import com.employees.model.enums.ErrorCode.ERROR_INVALID_CREDENTIALS
import com.employees.model.local.User
import com.employees.model.remote.UserDto
import com.employees.model.remote.toUser
import com.employees.utils.EmployeesException
import java.util.*
import kotlin.random.Random

class UserRepository(private val localDataSource: AppDataBase) {
    suspend fun login(email: String, password: String): User {
        val user = UserDto(UUID.randomUUID().toString(), email).toUser()
        localDataSource.withTransaction { localDataSource.userDao.insertItem(user) }
        simulateHttpError()
        return user
    }

    private fun simulateHttpError() {
        if (Random.nextBoolean()) {
            throw EmployeesException(ERROR_INVALID_CREDENTIALS)
        }
    }

    suspend fun getCurrentUser(): User? = localDataSource.withTransaction {
        localDataSource.userDao.getItem()
    }

    /**
     * Clears the users' table
     * @return true if the number of deleted rows is greater than or equal to 1
     */
    suspend fun logout(): Boolean = localDataSource.withTransaction {
        localDataSource.userDao.clearTable() >= 1
    }
}