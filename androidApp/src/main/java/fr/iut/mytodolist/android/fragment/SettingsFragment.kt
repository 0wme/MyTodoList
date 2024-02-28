package fr.iut.mytodolist.android.fragment

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.iut.mytodolist.android.R
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

        val openSettingsButton = view.findViewById<Button>(R.id.open_settings_button)
        openSettingsButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireActivity().packageName, null)
            intent.data = uri
            startActivity(intent)
        }


        val faqButton = view.findViewById<Button>(R.id.faq_button)
        faqButton.setOnClickListener {
            val faqDialog = AlertDialog.Builder(requireContext())
            faqDialog.setTitle("FAQ")
            faqDialog.setMessage(
                "Qui a crée DailyDo ?\n\n" +
                "C'est Tom Vieira, un étudiant en 2eme année en BUT Informatique à Limoges qui a crée cette app pendant un projet en Mobile.\n\n" +
                "Comment a-t-il fait DailyDo ?\n\n" +
                "Tom a fait l'application en Kotlin et peut être Swift pour IOS.\n\n" +
                "Comment on active/désactive les notifications ?\n\n" +
                "Pensez à aller dans les Paramètres de DailyDo et d'appuyer sur le bouton \"Open Settings\" pour vous emmener directement dans les Paramètres de l'application depuis votre Téléphone !\n\n" +
                "DailyDo est-elle compliqué à prendre en main ?\n\n" +
                "Cela dépend de si vous vous y connaissez, mais Tom a fait en sorte que ce soit le plus facile et le plair clair possible pour tout utilisateurs, débutant ou expérimentés !"
            )
            faqDialog.setPositiveButton("Contact") { _, _ ->
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/0wme"))
                startActivity(browserIntent)
            }
            faqDialog.setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            faqDialog.create().show()
        }

                return view
            }
        }