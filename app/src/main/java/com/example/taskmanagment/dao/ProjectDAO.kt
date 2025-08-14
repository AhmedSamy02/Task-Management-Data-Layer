package com.example.taskmanagment.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.taskmanagment.entities.Project
import com.example.taskmanagment.entities.ProjectWithTasks
import com.example.taskmanagment.entities.Task
import com.example.taskmanagment.entities.TaskWihProjects

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

    @Transaction
    @Query("Select * FROM Project")
    suspend fun getProjectsWithTasks(): List<ProjectWithTasks>

    @Query("Select * FROM Task WHERE projectId = :projectId")
    suspend fun getTasksOfProject(projectId: Int): List<Task>
}