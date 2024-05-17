package com.example.tasksmanager.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.tasksmanager.data.Task
import java.time.ZoneId


/**
 * AndroidAlarmScheduler
 * Klasa odpowiedzialna za planowanie alarmów za pomocą AlarmManager api w momencie gdy zbliża
 * się termin końcowy jednego z określonych przez użytkownika zadań
 * @constructor Tworzy nowy obiekt typu AndroidAlarmScheduler
 * @param context - kontekst aplikacji
 */
class AndroidAlarmScheduler(
    private val  context: Context
)
{
    /**
     * @param alarmManager - @  Alarm manager
     */
    private val alarmManager = context.getSystemService(AlarmManager::class.java);

    /**
     * Schedule
     * Metoda rejestruje alarm o dotyczący zbliżającego się terminu końcowego zadania które określił użytkownik
     * @param task - zadanie które zostanie użyte do określenia czasu aktywacji alarmu i którego tytuł zostanie przekazany przez alarm
     */
    fun schedule(task: Task) {
        val intent = Intent(context, AlarmReceiver::class.java).apply{
            putExtra("EXTRA_MESSAGE", task.title);
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            (task.getLocalDateTime().atZone(ZoneId.systemDefault()).toEpochSecond()-3600)*1000,
            pendingIntent
        )


    }

    /**
     * Cancel
     * Metoda anuluje powiadomienie o zbliżającym się terminie końcowym dla zadania określonego przez użytkownika
     * @param task - zadanie dla którego ma być anulowany alarm
     */
    fun cancel(task: Task) {
        val intent = Intent(context, AlarmReceiver::class.java).apply{
            putExtra("EXTRA_MESSAGE", task.title);
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,  task.id.hashCode(), intent,PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                task.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }


}