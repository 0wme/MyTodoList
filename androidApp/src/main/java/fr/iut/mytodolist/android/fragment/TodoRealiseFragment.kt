package fr.iut.mytodolist.android.fragment

import TodoDatabaseHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.mytodolist.android.R
import fr.iut.mytodolist.android.SharedViewModel
import fr.iut.mytodolist.android.TodoAdapter

class TodoRealiseFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_todo_realise, container, false)

        val dbHelper = TodoDatabaseHelper(requireActivity())
        val todos = dbHelper.getAllTodos()

        val approvedTodoList = todos.filter { it.status == "approved" }.toMutableList()

        val approvedTodoRecyclerView = view.findViewById<RecyclerView>(R.id.approvedTodoRecyclerView)
        approvedTodoRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TodoAdapter(approvedTodoList, null, null, requireActivity())
        approvedTodoRecyclerView.adapter = adapter

        return view
    }
}