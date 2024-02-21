package fr.iut.mytodolist.android

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val todoName = intent.getStringExtra("TODO_NAME") ?: "Your todo"
        val notificationHelper = NotificationHelper(context)
        val notification = notificationHelper.createNotification(todoName)
        with(NotificationManagerCompat.from(context)) {
            notify(0, notification.build())
        }
    }
}