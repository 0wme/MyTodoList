package fr.iut.mytodolist.android.todo

interface TodoApprovedListener {
    fun onTodoApproved(todo: String, dateTime: String)
    fun onTodoCancelled(todo: String, dateTime: String)
}