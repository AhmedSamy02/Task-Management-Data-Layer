package com.example.taskmanagment.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanagment.entities.Project

@Dao
interface ProjectDAO {
    @Insert
    suspend fun add(project: Project)

    @Delete
    suspend fun delete(project: Project)

    @Update
    suspend fun update(project: Project)

    @Query("Select * FROM Project")
    suspend fun getAll(): List<Project>
}