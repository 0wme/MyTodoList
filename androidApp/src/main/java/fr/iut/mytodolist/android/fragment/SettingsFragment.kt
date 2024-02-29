package fr.iut.mytodolist.android.fragment

import TodoDatabaseHelper
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
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import fr.iut.mytodolist.android.R
import java.util.Locale

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val logoImageView: ImageView = view.findViewById(R.id.logoImageView)
        Glide.with(this).load(R.drawable.logo_settings).into(logoImageView)

        val openSettingsButton = view.findViewById<Button>(R.id.open_settings_button)
        openSettingsButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", requireActivity().packageName, null)
            intent.data = uri
            startActivity(intent)
        }


        val faqButton = view.findViewById<Button>(R.id.faq_button)
        faqButton.setOnClickListener {
            val faqView = LayoutInflater.from(context).inflate(R.layout.faq_dialog_layout, null)

            val faqDialog = AlertDialog.Builder(requireContext(), R.style.AlertDialogCustom)
            faqDialog.setView(faqView)

            val contactButton = faqView.findViewById<Button>(R.id.contact_button)
            contactButton.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/0wme"))
                startActivity(browserIntent)
            }

            faqDialog.setNegativeButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            faqDialog.create().show()
        }


        val resetButton = view.findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            val resetDialog = AlertDialog.Builder(requireContext(),R.style.AlertDialogCustom)
            resetDialog.setTitle("Reset Database")
            resetDialog.setMessage("Are you sure you want to reset the database? This will delete all data.")
            resetDialog.setPositiveButton("Yes") { _, _ ->
                val dbHelper = TodoDatabaseHelper(requireContext())
                dbHelper.onUpgrade(dbHelper.writableDatabase, 1, 1)
                Toast.makeText(requireContext(), "Database reset successfully.", Toast.LENGTH_SHORT).show()
            }
            resetDialog.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            resetDialog.create().show()
        }


                return view
            }





}