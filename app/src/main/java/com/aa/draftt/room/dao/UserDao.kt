package com.aa.draftt.room.dao

import androidx.room.*
import com.aa.draftt.room.models.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM users WHERE user_id = :id")
    fun deleteById(id: Long)
}