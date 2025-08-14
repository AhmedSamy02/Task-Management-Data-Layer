package com.example.taskmanagment.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["title","ownerId"], unique = true)])
data class Project(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val ownerId: Int,
)