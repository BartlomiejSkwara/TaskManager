package com.example.tasksmanager.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.tasksmanager.data.Task
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val  context: Context
): AlarmScheduler
{

    private val alarmManager = context.getSystemService(AlarmManager::class.java);
    override fun schedule(task: Task) {
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

    override fun cancel(task: Task) {
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