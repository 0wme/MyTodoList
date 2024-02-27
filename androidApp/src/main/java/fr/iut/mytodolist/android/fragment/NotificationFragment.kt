package fr.iut.mytodolist.android.fragment

import TodoDatabaseHelper
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.mytodolist.android.NotificationAdapter
import fr.iut.mytodolist.android.R

class NotificationFragment : Fragment() {

    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private val notificationList = mutableListOf<String>()

    private lateinit var db: TodoDatabaseHelper

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val todoName = intent.getStringExtra("TODO_NAME")
            val timeLeft = intent.getStringExtra("TIME_LEFT")
            val notification = "La todo : $todoName est bientot en retard il vous reste : $timeLeft avant la fin !"
            addNotification(notification)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)

        db = TodoDatabaseHelper(requireContext())

        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView)
        notificationRecyclerView.layoutManager = LinearLayoutManager(context)
        notificationAdapter = NotificationAdapter(notificationList)
        notificationRecyclerView.adapter = notificationAdapter

        notificationList.addAll(db.getAllNotifications())
        notificationAdapter.notifyDataSetChanged()

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

    @SuppressLint("NotifyDataSetChanged")
    fun addNotification(notification: String) {
        activity?.runOnUiThread {
            notificationList.add(0, notification)
            notificationAdapter.notifyDataSetChanged()
        }
    }
}