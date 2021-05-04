package com.aa.draftt.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val user_id: Long,
    val name: String,
    val email: String
)