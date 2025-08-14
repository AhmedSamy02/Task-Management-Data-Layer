package com.example.taskmanagment.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanagment.entities.Attachment
import com.example.taskmanagment.entities.Project

@Dao
interface AttachmentDao {
    @Insert
    suspend fun add(attachment: Attachment)

    @Delete
    suspend fun delete(attachment: Attachment)

    @Update
    suspend fun update(attachment: Attachment)

    @Query("Select * FROM Attachment")
    suspend fun getAll(): List<Attachment>
}