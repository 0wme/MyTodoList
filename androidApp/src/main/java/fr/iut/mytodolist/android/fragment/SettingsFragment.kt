package fr.iut.mytodolist.android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import fr.iut.mytodolist.android.R
import android.content.res.Configuration
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.util.Locale

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val spinner = view.findViewById<Spinner>(R.id.language_spinner)
        val languages = arrayOf("English", "Français") // Ajoutez ici toutes les langues que vous voulez supporter
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val locale = when (position) {
                    0 -> Locale.ENGLISH
                    1 -> Locale.FRENCH
                    // Ajoutez ici tous les autres cas pour les autres langues
                    else -> Locale.ENGLISH
                }
                val config = Configuration()
                Locale.setDefault(locale)
                config.setLocale(locale)
                resources.updateConfiguration(config, resources.displayMetrics)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ne rien faire
            }
        }

        return view
    }
}