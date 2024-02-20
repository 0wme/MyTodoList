package fr.iut.mytodolist.android

interface TodoApprovedListener {
    fun onTodoApproved(todo: String, dateTime: String)
}
