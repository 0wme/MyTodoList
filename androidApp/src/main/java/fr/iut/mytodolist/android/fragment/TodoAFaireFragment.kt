package fr.iut.mytodolist.android.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.mytodolist.android.R
import fr.iut.mytodolist.android.TodoAdapter
import nl.dionsegijn.konfetti.KonfettiView

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

        val konfettiView = view.findViewById<KonfettiView>(R.id.viewKonfetti)

        val adapter = TodoAdapter(todoList, konfettiView)
        todoRecyclerView.adapter = adapter

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
                        adapter.buttonVisibilityList.add(View.GONE)
                        dialog.dismiss()
                        adapter.notifyDataSetChanged()
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
}