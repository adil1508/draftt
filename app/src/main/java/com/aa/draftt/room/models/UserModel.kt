package com.aa.draftt.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserModel(
    @PrimaryKey val user_id: Int,
    val name: String,
    val email: String
)