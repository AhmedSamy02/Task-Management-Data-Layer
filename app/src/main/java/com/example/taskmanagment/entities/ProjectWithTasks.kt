package com.example.taskmanagment.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ProjectWithTasks(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            ProjectTaskCrossRef::class,
            parentColumn = "projectId",
            entityColumn = "taskId",
        )
    )
    val tasks: List<Task>
)
