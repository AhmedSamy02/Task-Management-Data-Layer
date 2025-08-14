package com.example.taskmanagment.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanagment.entities.Task

@Dao
interface TaskDAO {
    @Insert
    suspend fun add(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)

    @Query("Select * FROM Task")
    suspend fun getAll(): List<Task>
}