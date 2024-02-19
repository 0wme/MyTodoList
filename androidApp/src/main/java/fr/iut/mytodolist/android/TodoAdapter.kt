package fr.iut.mytodolist.android

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import nl.dionsegijn.konfetti.KonfettiView

interface TodoApprovedListener {
    fun onTodoApproved(todo: String)
}

class TodoAdapter(private val todoList: MutableList<String>, private val konfettiView: KonfettiView? = null, private val listener: TodoApprovedListener? = null) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    val buttonVisibilityList = MutableList(todoList.size) { View.GONE }

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

        holder.buttonLayout.visibility = buttonVisibilityList[position]

        holder.todoTextView.setOnClickListener {
            if (holder.buttonLayout.visibility == View.GONE) {
                holder.buttonLayout.visibility = View.VISIBLE
                buttonVisibilityList[position] = View.VISIBLE
            } else {
                holder.buttonLayout.visibility = View.GONE
                buttonVisibilityList[position] = View.GONE
            }
            notifyItemChanged(position)
        }

        holder.approveButton.setOnClickListener {
            konfettiView?.build()
                ?.addColors(Color.BLUE, Color.WHITE, Color.RED)
                ?.setDirection(0.0, 359.0)
                ?.setSpeed(1f, 5f)
                ?.setFadeOutEnabled(true)
                ?.setTimeToLive(2000L)
                ?.addShapes(Shape.Square, Shape.Circle)
                ?.addSizes(Size(12))
                ?.setPosition(-1f, konfettiView.width + 1f, -1f, -1f)
                ?.streamFor(300, 5000L)
            removeAt(position)
            listener?.onTodoApproved(todo)
        }


        holder.cancelButton.setOnClickListener {
            // Handle cancel button click here
        }
    }

    override fun getItemCount() = todoList.size

    private fun removeAt(position: Int) {
        todoList.removeAt(position)
        buttonVisibilityList.removeAt(position)
        notifyItemRemoved(position)
    }
}