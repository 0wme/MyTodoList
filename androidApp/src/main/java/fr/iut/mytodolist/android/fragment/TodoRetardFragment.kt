package fr.iut.mytodolist.android.fragment

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
import fr.iut.mytodolist.android.DatabaseHelper

class TodoRetardFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        databaseHelper = DatabaseHelper(requireContext())
        val view = inflater.inflate(R.layout.fragment_todo_retard, container, false)

        val cancelledTodoList = databaseHelper.getCancelledTodos()
        val cancelledTodoRecyclerView = view.findViewById<RecyclerView>(R.id.cancelledTodoRecyclerView)
        cancelledTodoRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TodoAdapter(cancelledTodoList.map { it.first }.toMutableList(), cancelledTodoList.map { it.second }.toMutableList(), null, null, requireContext())
        cancelledTodoRecyclerView.adapter = adapter

        return view
    }
}