package com.example.tasksmanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.example.tasksmanager.data.OfflineTaskRepository
import com.example.tasksmanager.data.TaskManagerDB
import com.example.tasksmanager.data.TaskRepository

/**
 * My application
 * klasa dziedziczy po Application i przystosowuje je do potrzeb aplikacji
 */
class MyApplication(

): Application() {


    /**
     * On create
     * metoda aktywuje dodatkowe czynności które muszą zostać wykonane prz rozpoczęciu działania programu
     */
    override fun onCreate() {
        super.onCreate();
        createNotificationChannel();

    }


    /**
     * Create notification channel
     * metoda przygotowuje kanał na którym będą wysyłane powiadomienia dla użytkownika
     */
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

    /**
     * @param taskRepository - leniwa inicjalizacja bazy danych
     */
    val taskRepository: TaskRepository by lazy {
        OfflineTaskRepository(TaskManagerDB.getDatabase(this).taskDao())
    }

}