package fr.iut.mytodolist.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val todoList: MutableList<String>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val todoTextView: TextView = view.findViewById(R.id.todoTextView)
        val buttonLayout: LinearLayout = view.findViewById(R.id.buttonLayout)
        val approveButton: ImageButton = view.findViewById(R.id.approveButton)
        val cancelButton: ImageButton = view.findViewById(R.id.cancelButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        holder.todoTextView.text = todo

        holder.todoTextView.setOnClickListener {
            if (holder.buttonLayout.visibility == View.GONE) {
                holder.buttonLayout.visibility = View.VISIBLE
            } else {
                holder.buttonLayout.visibility = View.GONE
            }
            notifyItemChanged(position)
        }

        holder.approveButton.setOnClickListener {
            // Handle approve button click
        }

        holder.cancelButton.setOnClickListener {
            // Handle cancel button click
        }
    }

    override fun getItemCount() = todoList.size
}