package com.example.taskmanagment.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ProjectWithTasks(
    @Embedded val project: Project,
    @Relation(
        parentColumn = "projectId",
        entityColumn = "taskId",
        associateBy = Junction(ProjectTaskCrossRef::class)
    )
    val tasks: List<Task>
)
