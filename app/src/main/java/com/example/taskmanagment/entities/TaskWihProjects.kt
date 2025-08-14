package com.example.taskmanagment.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TaskWihProjects(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ProjectTaskCrossRef::class,
            parentColumn = "taskId",
            entityColumn = "projectId",
        )
    )
    val projects: List<Project>
)
