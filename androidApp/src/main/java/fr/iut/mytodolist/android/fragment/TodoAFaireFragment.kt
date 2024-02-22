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

class TodoAFaireFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        databaseHelper = DatabaseHelper(requireContext())
        val view = inflater.inflate(R.layout.fragment_todo_a_faire, container, false)

        val todoList = databaseHelper.getTodos()
        val todoRecyclerView = view.findViewById<RecyclerView>(R.id.todoRecyclerView)
        todoRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TodoAdapter(todoList.map { it.first }.toMutableList(), todoList.map { it.second }.toMutableList(), null, null, requireContext())
        todoRecyclerView.adapter = adapter

        return view
    }
}