package com.example.taskmanagment.testing

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.example.taskmanagment.dao.ProjectDAO
import com.example.taskmanagment.dao.ProjectTaskCrossRefDao
import com.example.taskmanagment.dao.TaskDAO
import com.example.taskmanagment.dao.getProjectsWithMoreThan3TasksQuery
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

suspend fun generateProjectsAndTasks(
    projectDAO: ProjectDAO,
    taskDAO: TaskDAO,
    projectTaskDao: ProjectTaskCrossRefDao
) {
    projectDAO.add(
        Project(
            title = "News app",
            ownerId = 1
        )
    )
    projectDAO.add(
        Project(
            title = "Weather app",
            ownerId = 2
        )
    )
    taskDAO.add(
        Task(
            description = "Implement Profile screen",
            projectId = 1
        )
    )
    taskDAO.add(
        Task(
            description = "Implement Register screen",
            projectId = 2
        )
    )
    taskDAO.add(
        Task(
            description = "Implement Cart screen",
            projectId = 2
        )
    )
    taskDAO.add(
        Task(
            description = "Implement Settings screen",
            projectId = 1
        )
    )
    taskDAO.add(
        Task(
            description = "Implement Settings screen",
            projectId = 2
        )
    )
    taskDAO.add(
        Task(
            description = "Implement Weather screen",
            projectId = 4
        )
    )
    projectTaskDao.add(ProjectTaskCrossRef(1, 4))
    projectTaskDao.add(ProjectTaskCrossRef(2, 5))
    projectTaskDao.add(ProjectTaskCrossRef(2, 6))
    projectTaskDao.add(ProjectTaskCrossRef(1, 7))
    projectTaskDao.add(ProjectTaskCrossRef(2, 8))
    projectTaskDao.add(ProjectTaskCrossRef(1, 9))
    projectDAO.add(Project(title = "E-Commerce Platform", ownerId = 3))
    projectDAO.add(Project(title = "Fitness Tracker", ownerId = 1))
    projectDAO.add(Project(title = "Music Streaming App", ownerId = 2))
    taskDAO.add(Task(description = "Setup Database Schema", projectId = 3))
    taskDAO.add(Task(description = "Design Landing Page", projectId = 3))
    taskDAO.add(Task(description = "Integrate Payment Gateway", projectId = 3))
    taskDAO.add(Task(description = "Implement Admin Dashboard", projectId = 3))
    taskDAO.add(Task(description = "Add Wishlist Feature", projectId = 3))
    taskDAO.add(Task(description = "Implement Step Counter", projectId = 4))
    taskDAO.add(Task(description = "Add Workout Logging", projectId = 4))
    taskDAO.add(Task(description = "Create Playlist Feature", projectId = 5))
    taskDAO.add(Task(description = "Implement Song Search", projectId = 5))
    taskDAO.add(Task(description = "Add Offline Mode", projectId = 5))
    projectTaskDao.add(ProjectTaskCrossRef(3, 7))
    projectTaskDao.add(ProjectTaskCrossRef(3, 8))
    projectTaskDao.add(ProjectTaskCrossRef(3, 9))
    projectTaskDao.add(ProjectTaskCrossRef(3, 10))
    projectTaskDao.add(ProjectTaskCrossRef(3, 11))
    projectTaskDao.add(ProjectTaskCrossRef(4, 12))
    projectTaskDao.add(ProjectTaskCrossRef(4, 13))
    projectTaskDao.add(ProjectTaskCrossRef(5, 14))
    projectTaskDao.add(ProjectTaskCrossRef(5, 15))
    projectTaskDao.add(ProjectTaskCrossRef(5, 16))
    projectDAO.add(Project(title = "Food Delivery App", ownerId = 1))
    projectDAO.add(Project(title = "Educational Platform", ownerId = 3))
    taskDAO.add(Task(description = "User Authentication Module", projectId = 6))
    taskDAO.add(Task(description = "Restaurant Listing Page", projectId = 6))
    taskDAO.add(Task(description = "Order Tracking Feature", projectId = 6))
    taskDAO.add(Task(description = "Payment Integration", projectId = 6))
    taskDAO.add(Task(description = "Push Notifications", projectId = 6))
    taskDAO.add(Task(description = "Create Course Upload Feature", projectId = 7))
    taskDAO.add(Task(description = "Implement Live Classes", projectId = 7))
    projectTaskDao.add(ProjectTaskCrossRef(6, 17))
    projectTaskDao.add(ProjectTaskCrossRef(6, 18))
    projectTaskDao.add(ProjectTaskCrossRef(6, 19))
    projectTaskDao.add(ProjectTaskCrossRef(6, 20))
    projectTaskDao.add(ProjectTaskCrossRef(6, 21))
    projectTaskDao.add(ProjectTaskCrossRef(7, 22))
    projectTaskDao.add(ProjectTaskCrossRef(7, 23))
}

/** Req 2.6. **/
suspend fun testQueryVsRawQuery(context: Context) {

    val db = AppDatabase.getDatabase(context)
    val dao = db.projectDao()
    try {
        generateProjectsAndTasks(dao, db.taskDao(), db.projectTaskDao())
    } catch (e: Exception) {

    }
    var start = System.nanoTime()
    for (i in 1..99) {
        dao.getProjectsWithMoreThan3Tasks()
    }
    var res = dao.getProjectsWithMoreThan3Tasks()
    var time = System.nanoTime() - start
    Log.d("PERF", "Result of Room query: $res")
    Log.d("PERF", "Room query: ${time}ns --> ${time / 1000000} ms")
    start = System.nanoTime()
    for (i in 1..99) {
        dao.getProjectsWithMoreThan3TasksRaw(getProjectsWithMoreThan3TasksQuery)
    }
    res = dao.getProjectsWithMoreThan3TasksRaw(getProjectsWithMoreThan3TasksQuery)
    time = System.nanoTime() - start
    Log.d("PERF", "Result of Raw query: $res")
    Log.d("PERF", "Raw query: ${time}ns --> ${time / 1000000} ms")
}
