package fr.iut.mytodolist.android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import fr.iut.mytodolist.android.R

class AddTodoFragment : Fragment() {

    private lateinit var editTextTodo: EditText
    private lateinit var buttonDate: Button
    private lateinit var buttonValidate: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_todo, container, false)

        editTextTodo = view.findViewById(R.id.editTextTodo)
        buttonDate = view.findViewById(R.id.buttonDate)
        buttonValidate = view.findViewById(R.id.buttonValidate)

        buttonValidate.setOnClickListener {
            val todoText = editTextTodo.text.toString()
            // Ajoutez le texte à votre liste de Todo ici
        }

        return view
    }
}