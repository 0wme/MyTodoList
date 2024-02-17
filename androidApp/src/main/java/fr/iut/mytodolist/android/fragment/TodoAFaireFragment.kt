package fr.iut.mytodolist.android.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.mytodolist.android.R

class TodoAFaireFragment : Fragment() {

    private val todoList = mutableListOf<String>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo_a_faire, container, false)

        val todoRecyclerView = view.findViewById<RecyclerView>(R.id.todoRecyclerView)
        todoRecyclerView.layoutManager = LinearLayoutManager(context)
        todoRecyclerView.adapter = TodoAdapter(todoList)

        val addTodoButton = view.findViewById<ImageButton>(R.id.addTodoButton)
        addTodoButton.setOnClickListener {
                    val dialogView = LayoutInflater.from(it.context).inflate(R.layout.dialog_layout, null)
                    val todoEditText = dialogView.findViewById<EditText>(R.id.dialogEditText)
                    val dialog = AlertDialog.Builder(it.context, R.style.AlertDialogCustom)
                    .setView(dialogView)
                    .setPositiveButton("Ajouter") { dialog, _ ->
                        val todo = todoEditText.text.toString()
                        if (todo.isNotEmpty()) {
                            todoList.add(todo)
                            dialog.dismiss()
                            (todoRecyclerView.adapter as TodoAdapter).notifyDataSetChanged()
                        }
                    }
                    .setNegativeButton("Annuler") { dialog, _ ->
                        dialog.cancel()
                    }
                    .create()
                dialog.show()
        }

        return view
    }

    class TodoAdapter(private val todoList: List<String>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

        class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val todoTextView: TextView = view.findViewById(R.id.todoTextView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
            return TodoViewHolder(view)
        }

        override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
            val todo = todoList[position]
            holder.todoTextView.text = todo
        }

        override fun getItemCount() = todoList.size
    }
}