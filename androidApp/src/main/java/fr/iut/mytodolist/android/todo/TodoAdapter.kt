package fr.iut.mytodolist.android.todo

import TodoDatabaseHelper
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.ImageButton
import android.widget.PopupMenu
import fr.iut.mytodolist.android.R
import fr.iut.mytodolist.android.SharedViewModel

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

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        holder.todoTextView.text = todo.todo
        holder.todoDateTimeTextView.text = todo.dateTime

        holder.todoTextView.setOnClickListener {
            if (!isApproving && todo.status == "pending") {
                (context as Activity).runOnUiThread {
                    val newVisibility = if (buttonVisibilityList[position] == View.GONE) View.VISIBLE else View.GONE
                    buttonVisibilityList[position] = newVisibility
                    holder.buttonLayout.visibility = newVisibility
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
                    ?.streamFor(301, 5000L)


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

        val optionsButton = holder.itemView.findViewById<ImageButton>(R.id.options_button)
        optionsButton.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.menuInflater.inflate(R.menu.todo_options_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.rename -> {
                    val editText = EditText(context)
                    AlertDialog.Builder(context)
                        .setTitle("Renommer l'élément")
                        .setView(editText)
                        .setPositiveButton("OK") { _, _ ->
                            val newName = editText.text.toString()
                            val updatedTodo = TodoDatabaseHelper.Todo(todo.id, newName, todo.dateTime, todo.status)
                            dbHelper.updateTodo(updatedTodo)
                            todoList[position] = updatedTodo
                            notifyDataSetChanged()
                        }
                        .setNegativeButton("Annuler", null)
                        .show()
                    }
                    R.id.share -> {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, todo.todo)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }
                    R.id.delete -> {
                        dbHelper.deleteTodo(todo.id)
                        removeAt(position)
                        notifyDataSetChanged()
                    }
                }
                true
            }
            popupMenu.show()
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTodoList: List<TodoDatabaseHelper.Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }
}