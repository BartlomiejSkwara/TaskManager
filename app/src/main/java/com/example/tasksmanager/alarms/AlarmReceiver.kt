package com.example.tasksmanager.alarms

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.tasksmanager.R

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val  message  = intent?.getStringExtra("EXTRA_MESSAGE")?:return
        if (context != null) {
            showNotification(context,message)
        };
    }


    private fun showNotification(context: Context, message: String){
        val notification = NotificationCompat.Builder(context,"channelId")
            .setContentText(context.getString(R.string.notificationTitle))
            .setContentTitle(context.getString(R.string.notification_content, message))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(1,notification);
    }

}