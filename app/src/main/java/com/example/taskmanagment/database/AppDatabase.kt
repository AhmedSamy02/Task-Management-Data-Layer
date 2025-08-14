package com.example.taskmanagment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.taskmanagment.dao.AttachmentDao
import com.example.taskmanagment.dao.ProjectDAO
import com.example.taskmanagment.dao.ProjectTaskCrossRefDao
import com.example.taskmanagment.dao.TaskDAO
import com.example.taskmanagment.dao.UserDAO
import com.example.taskmanagment.entities.Attachment
import com.example.taskmanagment.entities.Project
import com.example.taskmanagment.entities.ProjectTaskCrossRef
import com.example.taskmanagment.entities.Task
import com.example.taskmanagment.entities.User
import kotlin.concurrent.Volatile

@Database(
    entities = [Project::class, Task::class, User::class, Attachment::class, ProjectTaskCrossRef::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDAO
    abstract fun projectDao(): ProjectDAO
    abstract fun userDao(): UserDAO
    abstract fun attachmentDao(): AttachmentDao
    abstract fun projectTaskDao(): ProjectTaskCrossRefDao

    companion object {
        @Volatile
        private var _instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return _instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")

                    .fallbackToDestructiveMigration(false)
                    .build().also {
                        _instance = it
                    }
            }
        }


    }
}