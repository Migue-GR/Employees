package com.employees.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.employees.BuildConfig
import com.employees.model.local.Employee
import com.employees.model.local.User

@Database(
    version = BuildConfig.DB_VERSION,
    exportSchema = false,
    entities = [User::class, Employee::class]
)
abstract class AppDataBase : RoomDatabase() {
    abstract val userDao: UserDao
    abstract val employeesDao: EmployeesDao

    companion object {
        private const val DATABASE_NAME = "${BuildConfig.APPLICATION_ID}.AppDataBase"

        private fun createInstance(ctx: Context): AppDataBase =
            Room.databaseBuilder(ctx, AppDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(ctx: Context) = createInstance(ctx)
    }
}