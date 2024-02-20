package fr.iut.mytodolist.android.fragment

import fr.iut.mytodolist.android.SharedViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.iut.mytodolist.android.R
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.mytodolist.android.TodoAdapter

class TodoRealiseFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_todo_realise, container, false)

        sharedViewModel.approvedTodoList.observe(viewLifecycleOwner) { approvedTodoList ->
            val approvedTodoRecyclerView =
                view.findViewById<RecyclerView>(R.id.approvedTodoRecyclerView)
            approvedTodoRecyclerView.layoutManager = LinearLayoutManager(context)
            val adapter = TodoAdapter(approvedTodoList, mutableListOf(), null)
            approvedTodoRecyclerView.adapter = adapter
        }

        return view
    }

    fun addApprovedTodo() {
        val approvedTodoList = sharedViewModel.approvedTodoList.value
        val approvedTodoRecyclerView = view?.findViewById<RecyclerView>(R.id.approvedTodoRecyclerView)
        approvedTodoRecyclerView?.layoutManager = LinearLayoutManager(context)
        val adapter = TodoAdapter(approvedTodoList ?: mutableListOf(), mutableListOf())
        approvedTodoRecyclerView?.adapter = adapter
    }
}