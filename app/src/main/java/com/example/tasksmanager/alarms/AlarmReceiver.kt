package com.example.tasksmanager.alarms

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.tasksmanager.R

/**
 * Alarm receiver
 * Klasa zarejestrowana w AndroidManifesto.xml jako receiver i służąca do obsługiwania alarmów wysłanych przez
 * AlarmManager o nadchodzącym terminie końcowym jednego z zarejestrowanych przez użytkownika zadań
 * @constructor Tworzy nowy obiekt typu AlarmReceiver
 */
class AlarmReceiver: BroadcastReceiver() {


    /**
     * On receive
     * Implementacja metody potrzebnej do obsługiwania alaramów w momencie ich przyjścia
     * w celu powiadomienia programu o wysłaniu użytkownikowi przypomnienia o zbliżającym się terminie końcowym "zadania"
     * @param context - kontekst aplikacji
     * @param intent - intent przekazany dzięki alarmowi który przechowuje dane potrzebne do wygenerowania zawartości powiadomienia
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        val  message  = intent?.getStringExtra("EXTRA_MESSAGE")?:return
        if (context != null) {
            showNotification(context,message)
        };
    }


    /**
     * Show notification
     * Metoda odpowiada za stworzenie i przesłanie powiadomienia do użytkownika na przygotowanym w MyApplication kanale
     * wysłanie wymaga uprzedniego otrzymana zezwolenia przez użytkownika
     * @param context - kontekst aplikacji
     * @param message - wiadomość wydobyta z intent, zostanie częścią treści powiadaomienia
     */
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