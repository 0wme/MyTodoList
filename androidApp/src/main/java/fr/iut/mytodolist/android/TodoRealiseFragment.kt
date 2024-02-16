package fr.iut.mytodolist.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class TodoRealiseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Remplacez R.layout.fragment_todo_realise par le layout de votre fragment
        return inflater.inflate(R.layout.fragment_todo_realise, container, false)
    }
}