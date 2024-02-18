package fr.iut.mytodolist.android

import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import nl.dionsegijn.konfetti.KonfettiView
import androidx.core.content.ContextCompat

class TodoAdapter(val todoList: MutableList<String>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    val buttonVisibilityList = MutableList(todoList.size) { View.GONE }

    class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val todoTextView: TextView = view.findViewById(R.id.todoTextView)
        val buttonLayout: LinearLayout = view.findViewById(R.id.buttonLayout)
        val approveButton: ImageButton = view.findViewById(R.id.approveButton)
        val cancelButton: ImageButton = view.findViewById(R.id.cancelButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
    val todo = todoList[position]
    holder.todoTextView.text = todo

    holder.buttonLayout.visibility = buttonVisibilityList[position]

    holder.todoTextView.setOnClickListener {
        if (holder.buttonLayout.visibility == View.GONE) {
            holder.buttonLayout.visibility = View.VISIBLE
            buttonVisibilityList[position] = View.VISIBLE
        } else {
            holder.buttonLayout.visibility = View.GONE
            buttonVisibilityList[position] = View.GONE
        }
        notifyItemChanged(position)


        val konfettiView = holder.itemView.findViewById<nl.dionsegijn.konfetti.KonfettiView>(R.id.viewKonfetti)
        konfettiView.build()
            .addColors(Color.BLUE, Color.RED, Color.WHITE)
            .setDirection(0.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(2000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(konfettiView.width / 2f, konfettiView.height / 2f)
            .streamFor(300, 5000L)
    }

   holder.approveButton.setOnClickListener {
       val konfettiView = holder.itemView.findViewById<nl.dionsegijn.konfetti.KonfettiView>(R.id.viewKonfetti)
    konfettiView.visibility = View.VISIBLE // Make sure the KonfettiView is visible
    konfettiView.build()
        .addColors(Color.BLUE, Color.RED, Color.WHITE)
        .setDirection(0.0, 359.0)
        .setSpeed(1f, 5f)
        .setFadeOutEnabled(true)
        .setTimeToLive(2000L)
        .addShapes(Shape.Square, Shape.Circle)
        .addSizes(Size(12))
        .setPosition(konfettiView.width / 2f, konfettiView.height / 2f) // Set the position to the center of the KonfettiView
        .streamFor(300, 5000L)
}

    holder.cancelButton.setOnClickListener {
        // Handle cancel button click
    }
}
    override fun getItemCount() = todoList.size
}