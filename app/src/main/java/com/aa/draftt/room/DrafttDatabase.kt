package com.aa.draftt.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aa.draftt.room.dao.UserDao
import com.aa.draftt.room.models.User

@Database(entities = arrayOf(User::class), version = 1)
abstract class DrafttDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}