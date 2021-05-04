package com.aa.draftt.room.dao

import androidx.room.*
import com.aa.draftt.room.models.User
import kotlinx.coroutines.flow.Flow

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

    @Query("SELECT * FROM users WHERE user_id = :id")
    fun getUser(id: Long): Flow<User>
}