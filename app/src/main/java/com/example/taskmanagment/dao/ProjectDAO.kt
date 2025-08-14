package com.example.taskmanagment.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.RoomRawQuery
import androidx.room.Transaction
import androidx.room.Update
import com.example.taskmanagment.entities.Project
import com.example.taskmanagment.entities.ProjectWithTasks
import com.example.taskmanagment.entities.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface ProjectDAO {
    @Insert
    suspend fun add(project: Project)

    @Delete
    suspend fun delete(project: Project)

    @Update
    suspend fun update(project: Project)

    @Query("Select * FROM Project")
    suspend fun getAllProjectsOnce(): List<Project>

    @Query("Select * FROM Project")
    fun getAllProjectsFlow(): Flow<List<Project>>

    @Transaction
    @Query("Select * FROM Project")
    suspend fun getProjectsWithTasks(): List<ProjectWithTasks>

    @Query("Select * FROM Task WHERE projectId = :projectId")
    suspend fun getTasksOfProject(projectId: Int): List<Task>

    @Query("Select * FROM Project p JOIN ProjectTaskCrossRef pt ON p.id = pt.projectId Group By p.id Having Count(pt.projectId) >=3")
    suspend fun getProjectsWithMoreThan3Tasks(): List<Project>

    @RawQuery
    suspend fun getProjectsWithMoreThan3TasksRaw(query: RoomRawQuery): List<Project>
}

val getProjectsWithMoreThan3TasksQuery = RoomRawQuery(
    sql = "Select * FROM Project p JOIN ProjectTaskCrossRef pt ON p.id = pt.projectId Group By p.id Having Count(pt.projectId) >=3",
)
