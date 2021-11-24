package com.employees.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.employees.datasource.local.AppDataBase
import com.employees.model.local.User
import com.employees.model.remote.UserDto
import com.employees.model.remote.toUser
import timber.log.Timber
import java.util.*

class UserRepository(
    private val localDataSource: AppDataBase
) {
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    suspend fun login(email: String, password: String) {
        try {
            val user = UserDto(UUID.randomUUID().toString(), email).toUser()
            localDataSource.withTransaction { localDataSource.userDao.insertItem(user) }
            _user.postValue(user)
        } catch (e: Exception) {
            Timber.e(e, "Error while login with the credentials $email, $password")
            _user.postValue(null)
        }
    }

    suspend fun logout() = localDataSource.withTransaction {
        localDataSource.userDao.clearTable()
        _user.postValue(null)
    }

    suspend fun getCurrentUser() = localDataSource.withTransaction {
        _user.postValue(localDataSource.userDao.getItem())
    }
}