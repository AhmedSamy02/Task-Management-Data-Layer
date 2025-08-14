package com.example.taskmanagment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.taskmanagment.database.AppDatabase
import com.example.taskmanagment.testing.testInsertionInDatabase
import com.example.taskmanagment.testing.testQueryVsRawQuery
import com.example.taskmanagment.testing.testRetrieveProjectWithTasks
import com.example.taskmanagment.testing.testSuspendVsFlow
import com.example.taskmanagment.ui.theme.TaskManagmentTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch(Dispatchers.IO) {
            testInsertionInDatabase(applicationContext)
            testRetrieveProjectWithTasks(applicationContext)
            testSuspendVsFlow(applicationContext)
            testQueryVsRawQuery(applicationContext)
        }
        setContent {
            TaskManagmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskManagmentTheme {
        Greeting("Android")
    }
}