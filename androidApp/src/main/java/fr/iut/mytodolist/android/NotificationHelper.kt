package fr.iut.mytodolist.android

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationHelper(private val context: Context) {

    private val CHANNEL_ID = "TODO_NOTIFICATION_CHANNEL"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Todo Notifications"
        val descriptionText = "Notifications for todo items"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun createNotification(todoName: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Todo Reminder")
            .setContentText("$todoName is due in 24 hours!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
}