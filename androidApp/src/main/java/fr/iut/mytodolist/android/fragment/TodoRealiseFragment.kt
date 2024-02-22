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

class TodoRealiseFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        databaseHelper = DatabaseHelper(requireContext())
        val view = inflater.inflate(R.layout.fragment_todo_realise, container, false)

        val approvedTodoList = databaseHelper.getApprovedTodos()
        val approvedTodoRecyclerView = view.findViewById<RecyclerView>(R.id.approvedTodoRecyclerView)
        approvedTodoRecyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = TodoAdapter(approvedTodoList.map { it.first }.toMutableList(), approvedTodoList.map { it.second }.toMutableList(), null, null, requireContext())
        approvedTodoRecyclerView.adapter = adapter

        return view
    }
}