package fr.iut.mytodolist.android

import TodoDatabaseHelper
import android.app.Activity
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
    private val todoList: MutableList<TodoDatabaseHelper.Todo>,
    private val konfettiView: KonfettiView? = null,
    private val listener: TodoApprovedListener? = null,
    private val context: Context,
    private val sharedViewModel: SharedViewModel
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
        holder.todoTextView.text = todo.todo
        holder.todoDateTimeTextView.text = todo.dateTime

        holder.todoTextView.setOnClickListener {
            if (!isApproving) {
                (context as Activity).runOnUiThread {
                    val newVisibility = if (buttonVisibilityList[position] == View.GONE) View.VISIBLE else View.GONE
                    buttonVisibilityList[position] = newVisibility
                    holder.buttonLayout.visibility = newVisibility
                    notifyItemChanged(position)
                }
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
                        removeAt(position)
                        listener?.onTodoApproved(todo.todo, todo.dateTime)
                        dbHelper.updateTodoStatus(todo.id, "approved")
                        sharedViewModel.removeTodo(todo.todo)
                        isApproving = false
                    }
                }, 5000)
            }
        }

        holder.cancelButton.setOnClickListener {
            synchronized(this) {
                removeAt(position)
                listener?.onTodoCancelled(todo.todo, todo.dateTime)
                dbHelper.updateTodoStatus(todo.id, "cancelled")
                sharedViewModel.removeTodo(todo.todo)
            }
        }
    }

    override fun getItemCount() = todoList.size

    private fun removeAt(position: Int) {
        synchronized(this) {
            if (position < todoList.size) {
                todoList.removeAt(position)
                buttonVisibilityList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, todoList.size)
            }
        }
    }
}