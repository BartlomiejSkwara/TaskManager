package com.example.tasksmanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.tasksmanager.data.OfflineTaskRepository
import com.example.tasksmanager.data.TaskManagerDB
import com.example.tasksmanager.data.TaskRepository

class MyApplication(

): Application() {
    override fun onCreate() {
        super.onCreate();
        createNotificationChannel();

    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "channelId",
                "channelName",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel);
        }
    }

    val taskRepository: TaskRepository by lazy {
        OfflineTaskRepository(TaskManagerDB.getDatabase(this).taskDao())
    }

}