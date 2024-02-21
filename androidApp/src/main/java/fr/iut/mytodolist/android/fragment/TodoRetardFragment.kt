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

class TodoRetardFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_todo_retard, container, false)

        val cancelledTodoRecyclerView = view.findViewById<RecyclerView>(R.id.cancelledTodoRecyclerView)
        cancelledTodoRecyclerView.layoutManager = LinearLayoutManager(context)

        sharedViewModel.cancelledTodoList.observe(viewLifecycleOwner, Observer { cancelledTodos ->
            sharedViewModel.cancelledDateTimeList.observe(viewLifecycleOwner, Observer { cancelledDateTimes ->
                val adapter = TodoAdapter(cancelledTodos, cancelledDateTimes)
                cancelledTodoRecyclerView.adapter = adapter
            })
        })

        return view
    }
}