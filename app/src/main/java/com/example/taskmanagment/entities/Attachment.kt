package com.example.taskmanagment.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["filePath", "taskId"], unique = true)])
data class Attachment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val filePath: String,
    val taskId: Int
)
