package com.example.taskmanagment.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithProjects(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "ownerId"
    )
    val projects: List<Project>
)