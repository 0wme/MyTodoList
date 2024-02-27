package fr.iut.mytodolist.android

import TodoDatabaseHelper
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val todoName = intent.getStringExtra("TODO_NAME") ?: "Your todo"
        val notificationHelper = NotificationHelper(context)
        val notification = notificationHelper.createNotification(todoName)
        Log.d("AlarmReceiver", "About to send notification for $todoName")
        with(NotificationManagerCompat.from(context)) {
            notify(0, notification.build())
        }
        Log.d("AlarmReceiver", "Notification sent for $todoName")

        // Insert the notification into the database
        val db = TodoDatabaseHelper(context)
        val notificationText = "La todo : $todoName est bientot en retard il vous reste : 24 hours avant la fin !"
        db.insertNotification(notificationText)

        // Send a local broadcast
        val localIntent = Intent("TODO_NOTIFICATION").apply {
            putExtra("TODO_NAME", todoName)
            putExtra("TIME_LEFT", "24 hours") // Replace with actual time left
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(localIntent)
    }
}