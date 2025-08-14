package com.example.taskmanagment.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanagment.entities.User

@Dao
interface UserDAO {
    @Insert
    suspend fun add(user: User)

    @Delete
    suspend fun delete(user: User)

    @Update
    suspend fun update(user: User)

    @Query("Select * FROM user")
    suspend fun getAll(): List<User>
}