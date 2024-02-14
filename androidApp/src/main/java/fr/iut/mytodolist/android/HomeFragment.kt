package fr.iut.mytodolist.android

import android.annotation.SuppressLint
import android.app.AlertDialog
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
        val colorStateList = ContextCompat.getColorStateList(requireContext(), R.drawable.add_todo_state)
        addButton.imageTintList = colorStateList

        val listView = view.findViewById<ListView>(R.id.todo_list)
        val adapter = TodoListAdapter(todoList, todoIds, todoColors, todoApproved, dbHelper, requireContext(), R.layout.list_item)
        listView.adapter = adapter

        addButton.setOnClickListener {
            val editText = EditText(requireContext())
            val dialog = AlertDialog.Builder(requireContext())
                .setTitle("Que voulez-vous ajouter ?")
                .setView(editText)
                .setPositiveButton("Ajouter") { _, _ ->
                    val todo = editText.text.toString()
                    todoList.add(0, todo)
                    adapter.notifyDataSetChanged()

                    // Get the data repository in write mode
                    val db = dbHelper.writableDatabase

                    // Create a new map of values, where column names are the keys
                    val values = ContentValues().apply {
                        put(TodoContract.TodoEntry.COLUMN_TODO, todo)
                        put(TodoContract.TodoEntry.COLUMN_COLOR, ContextCompat.getColor(requireContext(), R.color.orange))
                        put(TodoContract.TodoEntry.COLUMN_APPROVED, 0) // Ajoutez l'état d'approbation à la base de données
                    }

                    // Insert the new row, returning the primary key value of the new row
                    val newRowId = db?.insert(TodoContract.TodoEntry.TABLE_NAME, null, values)
                    todoIds.add(0, newRowId?.toInt() ?: 0)
                    todoColors.add(0, ContextCompat.getColor(requireContext(), R.color.orange))
                    todoApproved.add(0, false)
                }
                .setNegativeButton("Annuler", null)
                .create()

            dialog.show()
        }

        // Get the data repository in read mode
        val db = dbHelper.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(BaseColumns._ID, TodoContract.TodoEntry.COLUMN_TODO, TodoContract.TodoEntry.COLUMN_COLOR, TodoContract.TodoEntry.COLUMN_APPROVED)

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
                val approved = getInt(getColumnIndexOrThrow(TodoContract.TodoEntry.COLUMN_APPROVED)) != 0
                todoList.add(todo)
                todoIds.add(id)
                todoColors.add(color)
                todoApproved.add(approved)
            }
        }

        return view
    }
}