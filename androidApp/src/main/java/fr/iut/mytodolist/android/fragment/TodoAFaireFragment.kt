package fr.iut.mytodolist.android.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.iut.mytodolist.android.AlarmReceiver
import fr.iut.mytodolist.android.R
import fr.iut.mytodolist.android.SharedViewModel
import fr.iut.mytodolist.android.TodoAdapter
import fr.iut.mytodolist.android.TodoApprovedListener
import nl.dionsegijn.konfetti.KonfettiView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TodoAFaireFragment : Fragment(), TodoApprovedListener {

    private val todoList = mutableListOf<String>()
    private val dateTimeList = mutableListOf<String>()
    private lateinit var sharedViewModel: SharedViewModel

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n", "ScheduleExactAlarm")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_todo_a_faire, container, false)

        val todoRecyclerView = view.findViewById<RecyclerView>(R.id.todoRecyclerView)
        todoRecyclerView.layoutManager = LinearLayoutManager(context)

        val konfettiView = view.findViewById<KonfettiView>(R.id.viewKonfetti)

        val adapter = TodoAdapter(todoList, dateTimeList, konfettiView, this)
        todoRecyclerView.adapter = adapter

        val addTodoButton = view.findViewById<ImageButton>(R.id.addTodoButton)
        addTodoButton.setOnClickListener {
            val dialogView = LayoutInflater.from(it.context).inflate(R.layout.dialog_layout, null)
            val todoEditText = dialogView.findViewById<EditText>(R.id.dialogEditText)
            val dateButton = dialogView.findViewById<Button>(R.id.dateButton)
            val timeButton = dialogView.findViewById<Button>(R.id.timeButton)

            dateButton.setOnClickListener {
                val calendar = Calendar.getInstance()
                val datePickerDialog = DatePickerDialog(it.context, { _, year, month, dayOfMonth ->
                    dateButton.text = "$dayOfMonth/$month/$year"
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                datePickerDialog.show()
            }

            timeButton.setOnClickListener {
                val calendar = Calendar.getInstance()
                val timePickerDialog = TimePickerDialog(it.context, { _, hourOfDay, minute ->
                    timeButton.text = "$hourOfDay:$minute"
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
                timePickerDialog.show()
            }

            val dialog = AlertDialog.Builder(it.context, R.style.AlertDialogCustom)
                .setView(dialogView)
.setPositiveButton("Ajouter") { _, _ ->
    val todo = todoEditText.text.toString()
    var dateTime = ""
    if (dateButton.text != "Choisir une date") {
        dateTime += dateButton.text
    }
    if (timeButton.text != "Choisir une heure") {
        if (dateTime.isNotEmpty()) {
            dateTime += " "
        }
        dateTime += timeButton.text
    }
    if (todo.isNotEmpty()) {
        todoList.add(todo)
        dateTimeList.add(dateTime)
        adapter.buttonVisibilityList.add(View.GONE)
        adapter.notifyDataSetChanged()

        // Check for permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                    putExtra("android.provider.extra.PACKAGE_NAME", context?.packageName)
                }
                startActivity(intent)
            } else {
                scheduleAlarm(todo, dateTime, it.context)
            }
        } else {
            scheduleAlarm(todo, dateTime, it.context)
        }
    }
}
                .setNegativeButton("Annuler") { dialog, _ ->
                    dialog.cancel()
                }
                .create()
            dialog.show()
        }

        return view
    }

    private fun scheduleAlarm(todo: String, dateTime: String, context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("TODO_NAME", todo)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent,
            PendingIntent.FLAG_IMMUTABLE)

        val format = if (dateTime.contains(":")) {
            SimpleDateFormat("d/M/yyyy HH:mm", Locale.getDefault())
        } else {
            SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        }
        if (dateTime.isNotEmpty()) {
            val date = format.parse(dateTime)
            val alarmTime = date?.time?.minus(TimeUnit.HOURS.toMillis(24))

            if (alarmTime != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent)
            }
        }
    }

    override fun onTodoApproved(todo: String, dateTime: String) {
        sharedViewModel.approvedTodoList.value?.let {
            it.add(todo)
            sharedViewModel.approvedTodoList.postValue(it)
        }
        sharedViewModel.approvedDateTimeList.value?.let {
            it.add(dateTime)
            sharedViewModel.approvedDateTimeList.postValue(it)
        }
    }

    override fun onTodoCancelled(todo: String, dateTime: String) {
        sharedViewModel.cancelledTodoList.value?.let {
            it.add(todo)
            sharedViewModel.cancelledTodoList.postValue(it)
        }
        sharedViewModel.cancelledDateTimeList.value?.let {
            it.add(dateTime)
            sharedViewModel.cancelledDateTimeList.postValue(it)
        }
    }
}