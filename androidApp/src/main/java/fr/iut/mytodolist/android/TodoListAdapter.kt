package fr.iut.mytodolist.android
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class TodoListAdapter(private val todoList: ArrayList<String>, private val todoIds: ArrayList<Int>, private val todoColors: ArrayList<Int>, private val todoApproved: ArrayList<Boolean>, private val dbHelper: TodoDatabaseHelper, context: Context, resource: Int) :
    ArrayAdapter<String>(context, resource, todoList) {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        val todoText = view.findViewById<TextView>(R.id.todo_text)
        val optionsButton = view.findViewById<ImageButton>(R.id.options_button)
        val approveButton = view.findViewById<ImageButton>(R.id.approve_button)
        val cancelButton = view.findViewById<ImageButton>(R.id.cancel_button)

        todoText.text = getItem(position)
        view.setBackgroundColor(todoColors[position]) // Mettez la couleur de la vue à la couleur stockée

        approveButton.isEnabled = !todoApproved[position] // Désactivez le bouton si le todo est approuvé

        todoText.setOnClickListener {
            if (approveButton.visibility == View.GONE) {
                approveButton.visibility = View.VISIBLE
                cancelButton.visibility = View.VISIBLE
                todoText.textSize = 24f
            } else {
                approveButton.visibility = View.GONE
                cancelButton.visibility = View.GONE
                todoText.textSize = 16f
            }
        }

        approveButton.setOnClickListener {
            val color = ContextCompat.getColor(context, R.color.green)
            view.setBackgroundColor(color)
            todoColors[position] = color
            todoApproved[position] = true
            dbHelper.updateItem(todoIds[position], todoList[position], color, if (todoApproved[position]) 1 else 0)

            val konfettiView = (context as Activity).findViewById<nl.dionsegijn.konfetti.KonfettiView>(R.id.viewKonfetti)
            konfettiView.build()
                .addColors(Color.BLUE, Color.RED, Color.WHITE)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square, Shape.Circle)
                .addSizes(Size(12))
                .setPosition(-50f, konfettiView.width + 50f, -50f, -50f)
                .streamFor(300, 5000L)

            approveButton.isEnabled = false
        }

        cancelButton.setOnClickListener {
            val color = ContextCompat.getColor(context, R.color.red)
            view.setBackgroundColor(color)
            todoColors[position] = color
            todoApproved[position] = false
            dbHelper.updateItem(todoIds[position], todoList[position], color, if (todoApproved[position]) 1 else 0)

            approveButton.isEnabled = true
        }

        optionsButton.setOnClickListener {
            val popupMenu = PopupMenu(context, it)
            popupMenu.menuInflater.inflate(R.menu.todo_options_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.rename -> {
                        val editText = EditText(context)
                        AlertDialog.Builder(context)
                            .setTitle("Renommer l'élément")
                            .setView(editText)
                            .setPositiveButton("OK") { _, _ ->
                                val newName = editText.text.toString()
                                todoList[position] = newName
                                dbHelper.updateItem(todoIds[position], newName, todoColors[position], if (todoApproved[position]) 1 else 0)
                                notifyDataSetChanged()
                            }
                            .setNegativeButton("Annuler", null)
                            .show()
                    }
                    R.id.modify -> {
                        val editText = EditText(context)
                        AlertDialog.Builder(context)
                            .setTitle("Modifier l'élément")
                            .setView(editText)
                            .setPositiveButton("OK") { _, _ ->
                                val newContent = editText.text.toString()
                                todoList[position] = newContent
                                dbHelper.updateItem(todoIds[position], newContent, todoColors[position], if (todoApproved[position]) 1 else 0)
                                notifyDataSetChanged()
                            }
                            .setNegativeButton("Annuler", null)
                            .show()
                    }
                    R.id.share -> {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, todoList[position])
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    }
                    R.id.delete -> {
                        dbHelper.deleteItem(todoIds[position])
                        todoIds.removeAt(position)
                        todoList.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
                true
            }
            popupMenu.show()
        }

        return view
    }
}