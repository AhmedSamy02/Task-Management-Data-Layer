package com.example.taskmanagment.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import com.example.taskmanagment.entities.ProjectTaskCrossRef

@Dao
interface ProjectTaskCrossRefDao {
    @Insert
    suspend fun add(projectTask: ProjectTaskCrossRef)

    @Delete
    suspend fun delete(projectTask: ProjectTaskCrossRef)
}