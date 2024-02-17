package fr.iut.mytodolist.android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import fr.iut.mytodolist.android.R

class TodoAFaireFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo_a_faire, container, false)

        val addTodoButton = view.findViewById<ImageButton>(R.id.addTodoButton)
        addTodoButton.setOnClickListener {
            val fragmentManager = fragmentManager
            fragmentManager?.let {
                val fragmentTransaction = it.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, AddTodoFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        return view
    }
}