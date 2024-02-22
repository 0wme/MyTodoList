package fr.iut.mytodolist.android.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import fr.iut.mytodolist.android.R

class NotificationFragment : Fragment() {

    private lateinit var textView: TextView
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val todoName = intent.getStringExtra("TODO_NAME")
            val timeLeft = intent.getStringExtra("TIME_LEFT")
            textView.text = "La todo : $todoName est bientot en retard il vous reste : $timeLeft avant la fin !"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        textView = view.findViewById(R.id.text)
        return view
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(receiver, IntentFilter("TODO_NOTIFICATION"))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(receiver)
    }
}