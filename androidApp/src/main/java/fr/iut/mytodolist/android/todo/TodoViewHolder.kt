package fr.iut.mytodolist.android.todo

import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.iut.mytodolist.android.R

class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val todoTextView: TextView = view.findViewById(R.id.todoTextView)
    val todoDateTimeTextView: TextView = view.findViewById(R.id.todoDateTimeTextView)
    val buttonLayout: LinearLayout = view.findViewById(R.id.buttonLayout)
    val approveButton: ImageButton = view.findViewById(R.id.approveButton)
    val cancelButton: ImageButton = view.findViewById(R.id.cancelButton)
}