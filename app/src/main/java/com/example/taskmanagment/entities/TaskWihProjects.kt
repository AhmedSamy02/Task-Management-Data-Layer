package com.example.taskmanagment.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TaskWihProjects(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "projectId",
        associateBy = Junction(ProjectTaskCrossRef::class)
    )
    val projects: List<Project>
)
