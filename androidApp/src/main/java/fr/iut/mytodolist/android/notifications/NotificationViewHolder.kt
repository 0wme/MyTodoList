package fr.iut.mytodolist.android.notifications

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.iut.mytodolist.android.R

class NotificationViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val notificationTextView: TextView = view.findViewById(R.id.notificationTextView)
    val cancelButton: ImageButton = view.findViewById(R.id.cancelButton)
}