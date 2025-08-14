package com.example.taskmanagment.entities

import androidx.room.Entity


@Entity(primaryKeys = ["projectId", "taskId"])
data class ProjectTaskCrossRef(
    val projectId: Int,
    val taskId: Int
)
