package fr.iut.mytodolist.android

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    private val todoList = ArrayList<String>()
    private val todoIds = ArrayList<Int>()
    private val todoColors = ArrayList<Int>()
    private val todoApproved = ArrayList<Boolean>()

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val dbHelper = TodoDatabaseHelper(requireContext())

        val addButton = view.findViewById<ImageButton>(R.id.add_todo_button)
        val colorStateList =
            ContextCompat.getColorStateList(requireContext(), R.drawable.add_todo_state)
        addButton.imageTintList = colorStateList

        val listView = view.findViewById<ListView>(R.id.todo_list)
        val todoDeadlines = ArrayList<String>()
        val adapter = TodoListAdapter(
            todoList,
            todoIds,
            todoColors,
            todoApproved,
            todoDeadlines,
            dbHelper,
            requireContext(),
            R.layout.list_item
        )
        listView.adapter = adapter

        addButton.setOnClickListener {
            val editText = EditText(requireContext())
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Que voulez-vous ajouter ?")
                .setView(editText)
                .setPositiveButton("Ajouter") { _, _ ->
                    val todo = editText.text.toString()
                    val datePicker = DatePickerDialog(requireContext())
                    datePicker.setOnDateSetListener { _, year, month, dayOfMonth ->
                        val deadline = "$year-${month + 1}-$dayOfMonth"
                        todoList.add(0, todo)
                        todoDeadlines.add(0, deadline) // Ajoutez la date limite à todoDeadlines ici
                        adapter.notifyDataSetChanged()

                        // Get the data repository in write mode
                        val db = dbHelper.writableDatabase

                        // Create a new map of values, where column names are the keys
                        val values = ContentValues().apply {
                            put(TodoContract.TodoEntry.COLUMN_TODO, todo)
                            put(TodoContract.TodoEntry.COLUMN_COLOR, ContextCompat.getColor(requireContext(), R.color.orange))
                            put(TodoContract.TodoEntry.COLUMN_APPROVED, 0)
                            put(TodoContract.TodoEntry.COLUMN_DEADLINE, deadline)
                        }

                        // Insert the new row, returning the primary key value of the new row
                        val newRowId = db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
                        todoIds.add(0, newRowId?.toInt() ?: 0)
                        todoColors.add(0, ContextCompat.getColor(requireContext(), R.color.orange))
                        todoApproved.add(0, false)
                    }
                    datePicker.show()
                }
                .setNegativeButton("Annuler", null)
                .create()

            dialog.show()
        }

        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            TodoContract.TodoEntry.COLUMN_TODO,
            TodoContract.TodoEntry.COLUMN_COLOR,
            TodoContract.TodoEntry.COLUMN_APPROVED,
            TodoContract.TodoEntry.COLUMN_DEADLINE
        )

        // Perform a query on the todo table
        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val todo = getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_TODO))
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val color = getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_COLOR))
                val approved =
                    getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_APPROVED)) != 0
                val deadline = getString(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_DEADLINE))
                todoList.add(todo)
                todoIds.add(id)
                todoColors.add(color)
                todoApproved.add(approved)
                todoDeadlines.add(deadline)
            }
        }

        return view
    }
}