package fr.iut.mytodolist.android

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val notificationTextView: TextView = view.findViewById(R.id.notificationTextView)
}