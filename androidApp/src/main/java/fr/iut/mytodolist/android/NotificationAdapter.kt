package fr.iut.mytodolist.android

import TodoDatabaseHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter(private val notificationList: MutableList<Pair<Int, String>>, private val db: TodoDatabaseHelper) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val notificationTextView: TextView = view.findViewById(R.id.notificationTextView)
        val cancelButton: ImageButton = view.findViewById(R.id.cancelButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notificationList[position]
        holder.notificationTextView.text = notification.second

        holder.cancelButton.setOnClickListener {
            // Supprime la notification de la base de données
            db.deleteNotification(notification.first)
            // Supprime la notification de la liste et met à jour l'adapter
            notificationList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = notificationList.size
}