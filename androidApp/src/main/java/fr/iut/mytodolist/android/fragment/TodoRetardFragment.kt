package fr.iut.mytodolist.android.fragment

import TodoDatabaseHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.mytodolist.android.R
import fr.iut.mytodolist.android.SharedViewModel
import fr.iut.mytodolist.android.todo.TodoAdapter

class TodoRetardFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_todo_retard, container, false)

        val dbHelper = TodoDatabaseHelper(requireActivity())
        val todos = dbHelper.getAllTodos()

        val cancelledTodoList = todos.filter { it.status == "cancelled" }.toMutableList()

        val cancelledTodoRecyclerView = view.findViewById<RecyclerView>(R.id.cancelledTodoRecyclerView)
        cancelledTodoRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = TodoAdapter(cancelledTodoList, null, null, requireActivity(), sharedViewModel)
        cancelledTodoRecyclerView.adapter = adapter

        // Observer pour la liste des tâches retardées
        sharedViewModel.cancelledTodoList.observe(viewLifecycleOwner, Observer {
            updateCancelledTodos()
        })

        return view
    }

    private fun updateCancelledTodos() {
        val dbHelper = TodoDatabaseHelper(requireActivity())
        val updatedTodos = dbHelper.getAllTodos().filter { it.status == "cancelled" }.toMutableList()
        adapter.updateData(updatedTodos)
    }
}
