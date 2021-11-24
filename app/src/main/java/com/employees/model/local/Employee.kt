package com.employees.model.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Employees")
data class Employee(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val imageUri: String,
    val description: String,
    val rating: Float
) : Parcelable {
    val fullName: String
        get() = "$firstName $lastName"
}