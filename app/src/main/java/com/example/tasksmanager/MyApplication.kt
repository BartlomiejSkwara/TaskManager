package com.example.tasksmanager

import android.app.Application
import com.example.tasksmanager.data.OfflineTaskRepository
import com.example.tasksmanager.data.TaskManagerDB
import com.example.tasksmanager.data.TaskRepository

class MyApplication(

): Application() {


    val taskRepository: TaskRepository by lazy {
        OfflineTaskRepository(TaskManagerDB.getDatabase(this).taskDao())
    }

}