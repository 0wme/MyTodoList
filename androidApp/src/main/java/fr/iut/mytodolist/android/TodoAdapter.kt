package fr.iut.mytodolist.android

import TodoDatabaseHelper
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import android.os.Handler
import android.os.Looper

class TodoAdapter(
    private val todoList: MutableList<String>,
    private val dateTimeList: MutableList<String>,
    private val konfettiView: KonfettiView? = null,
    private val listener: TodoApprovedListener? = null,
    private val context: Context
) : RecyclerView.Adapter<TodoViewHolder>() {

    val buttonVisibilityList = MutableList(todoList.size) { View.GONE }
    private var isApproving = false
    private val handler = Handler(Looper.getMainLooper())
    private val dbHelper = TodoDatabaseHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        holder.todoTextView.text = todo
        if (position < dateTimeList.size) {
            holder.todoDateTimeTextView.text = dateTimeList[position]
        } else {
            holder.todoDateTimeTextView.text = ""
        }

        holder.todoTextView.setOnClickListener {
            if (!isApproving) {
                val newVisibility = if (buttonVisibilityList[position] == View.GONE) View.VISIBLE else View.GONE
                buttonVisibilityList[position] = newVisibility
                notifyItemChanged(position)
            }
        }

        holder.approveButton.setOnClickListener {
            if (!isApproving) {
                isApproving = true

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

                handler.postDelayed({
                    synchronized(this) {
                        val dateTime = if (position < dateTimeList.size) dateTimeList[position] else ""
                        removeAt(position)
                        listener?.onTodoApproved(todo, dateTime)
                        dbHelper.insertTodo(todo, dateTime, "approved")
                        isApproving = false
                    }
                }, 5000)
            }
        }

        holder.buttonLayout.visibility = buttonVisibilityList[position]

        holder.cancelButton.setOnClickListener {
            synchronized(this) {
                val dateTime = if (position < dateTimeList.size) dateTimeList[position] else ""
                val todo = todoList[position]
                removeAt(position)
                listener?.onTodoCancelled(todo, dateTime)
                dbHelper.insertTodo(todo, dateTime, "cancelled")
            }
        }
    }

    override fun getItemCount() = todoList.size

    private fun removeAt(position: Int) {
        synchronized(this) {
            if (position < todoList.size) {
                val todo = todoList[position]
                val dateTime = dateTimeList[position]
                todoList.removeAt(position)
                dateTimeList.removeAt(position)
                buttonVisibilityList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, todoList.size)
                dbHelper.deleteTodo(todo.hashCode())
            }
        }
    }
}