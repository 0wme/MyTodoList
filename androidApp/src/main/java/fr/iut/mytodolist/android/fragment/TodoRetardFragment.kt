package fr.iut.mytodolist.android.fragment

import TodoDatabaseHelper
import android.annotation.SuppressLint
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
import fr.iut.mytodolist.android.TodoAdapter

class TodoRetardFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    @SuppressLint("NotifyDataSetChanged")
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
        val adapter = TodoAdapter(cancelledTodoList, null, null, requireActivity())
        cancelledTodoRecyclerView.adapter = adapter

        // Observe cancelledTodoList in sharedViewModel
        sharedViewModel.cancelledTodoList.observe(viewLifecycleOwner) { cancelledTodos ->
            val cancelledTodoItems = cancelledTodos.map { todo ->
                TodoDatabaseHelper.Todo(0, todo, "", "cancelled")
            }
            cancelledTodoList.clear()
            cancelledTodoList.addAll(cancelledTodoItems)
            adapter.notifyDataSetChanged()
        }

        return view
    }
}