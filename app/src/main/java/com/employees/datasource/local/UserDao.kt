package com.employees.datasource.local

import androidx.room.*
import com.employees.model.local.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: User)

    @Query("SELECT * FROM Users LIMIT 1")
    suspend fun getItem(): User?

    @Query("DELETE FROM Users")
    fun clearTable()
}