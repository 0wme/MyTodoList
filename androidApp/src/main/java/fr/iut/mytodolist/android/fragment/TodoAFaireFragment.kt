package fr.iut.mytodolist.android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import fr.iut.mytodolist.android.R

class TodoAFaireFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Remplacez R.layout.fragment_todo_a_faire par le layout de votre fragment
        return inflater.inflate(R.layout.fragment_todo_a_faire, container, false)
    }
}