package com.example.taskmanagment.testing

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.example.taskmanagment.database.AppDatabase
import com.example.taskmanagment.entities.Attachment
import com.example.taskmanagment.entities.Project
import com.example.taskmanagment.entities.ProjectTaskCrossRef
import com.example.taskmanagment.entities.Task
import com.example.taskmanagment.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

/** Req 2.2.**/
suspend fun testInsertionInDatabase(context: Context) {
    Log.d("DB_TEST", "--- Start of testing ---")
    val db = AppDatabase.getDatabase(context)

    try {
        val user1 = User(name = "Ahmed", email = "ahmed.samy@yahoo.com")
        db.userDao().add(user1)
        Log.d("DB_TEST", "User added $user1")
        val user2 = User(name = "Akram", email = "akram.mohamed@gmail.com")
        db.userDao().add(user2)
        Log.d("DB_TEST", "User added $user2")
        Log.d("DB_TEST", "Trying to add same user with same email again")
        db.userDao().add(user1)
    } catch (e: SQLiteConstraintException) {
        Log.d("DB_TEST", "Failed to add user ${e.localizedMessage}")
    }
    try {
        val project1 = Project(
            title = "Task management",
            ownerId = 1,
        )
        db.projectDao().add(project1)
        Log.d("DB_TEST", "Project added $project1")
        val project2 = Project(
            title = "TODO app",
            ownerId = 1,
        )
        db.projectDao().add(project2)
        Log.d("DB_TEST", "Project added $project2")
    } catch (e: SQLiteConstraintException) {
        Log.d("DB_TEST", "Failed to add user ${e.localizedMessage}")
    }
    try {
        val task1 = Task(
            description = "Implement Login screen",
            projectId = 1
        )
        db.taskDao().add(task1)
        Log.d("DB_TEST", "Task added $task1")
        val task2 = Task(
            description = "Implement Home screen",
            projectId = 1
        )
        db.taskDao().add(task2)

        Log.d("DB_TEST", "Task added $task2")
        val task3 = Task(
            description = "Implement Profile screen",
            projectId = 2
        )
        db.taskDao().add(task3)
        Log.d("DB_TEST", "Task added $task3")

    } catch (e: SQLiteConstraintException) {
        Log.d("DB_TEST", "Failed to add Task ${e.localizedMessage}")
    }
    val attachment = Attachment(
        filePath = "/images/1.jpg",
        taskId = 1
    )
    try {
        db.attachmentDao().add(attachment)
        Log.d("DB_TEST", "Attachment added $attachment")
    } catch (e: SQLiteConstraintException) {
        Log.d("DB_TEST", "Failed to add Attachment ${e.localizedMessage}")
    }
    try {
        val projectTaskDao = db.projectTaskDao()
        projectTaskDao.add(ProjectTaskCrossRef(1, 1))
        projectTaskDao.add(ProjectTaskCrossRef(1, 2))
        projectTaskDao.add(ProjectTaskCrossRef(2, 3))
    } catch (e: Exception) {

    }
    Log.d("DB_TEST", "--- End of testing ---")
}

/** Req 2.3. **/
suspend fun testRetrieveProjectWithTasks(context: Context) {
    try {
        val db = AppDatabase.getDatabase(context)
        val projectDao = db.projectDao()
        val taskInProject = projectDao.getProjectsWithTasks()
        taskInProject.forEach {
            Log.d("DB_TEST", "For project ${it.project} Tasks are = ${it.tasks}")
        }
        Log.d("DB_TEST", "------ Trying function getTasksOfProject on project with id = 1 ------")
        projectDao.getTasksOfProject(1).forEach {
            Log.d("DB_TEST", "Task = $it")
        }


    } catch (e: SQLiteConstraintException) {
        Log.d("DB_TEST", "Failed to in retrieving ${e.localizedMessage}")

    }
}

/** Req 2.4. **/
suspend fun testSuspendVsFlow(context: Context) {
    val dao = AppDatabase.getDatabase(context).projectDao()
    var projects = dao.getAllProjectsOnce()
    Log.d("DAO_TEST", "Suspend projects: $projects")
    val job = GlobalScope.launch(Dispatchers.IO) {
        dao.getAllProjectsFlow()
            .collect {
                projects = it
                Log.d("DAO_TEST", "Flow emission: $it")
            }
    }
    delay(2000)
    job.cancel()
    Log.d("DAO_TEST", "Flow emission: Closed")


}
