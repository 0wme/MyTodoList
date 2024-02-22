import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 2

        private const val TABLE_TODO = "todo"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TODO = "todo"
        private const val COLUMN_DATETIME = "datetime"
        private const val COLUMN_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableTodo = "CREATE TABLE $TABLE_TODO (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_TODO TEXT, " +
                "$COLUMN_DATETIME TEXT, " +
                "$COLUMN_STATUS TEXT)"

        db.execSQL(createTableTodo)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TODO")
        onCreate(db)
    }

    fun insertTodo(todo: String, dateTime: String, status: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TODO, todo)
        values.put(COLUMN_DATETIME, dateTime)
        values.put(COLUMN_STATUS, status)
        val result = db.insert(TABLE_TODO, null, values)
        db.close()
        return result
    }

    data class Todo(val id: Int, val todo: String, val dateTime: String, val status: String)

    @SuppressLint("Range")
    fun getAllTodos(): List<Todo> {
        val todos = mutableListOf<Todo>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TODO", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                val todo = cursor.getString(cursor.getColumnIndex(COLUMN_TODO))
                val dateTime = cursor.getString(cursor.getColumnIndex(COLUMN_DATETIME))
                val status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS))
                todos.add(Todo(id, todo, dateTime, status))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return todos
    }

    fun updateTodoStatus(id: Int, status: String): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_STATUS, status)
        val result = db.update(TABLE_TODO, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun deleteTodo(id: Int): Int {
        val db = this.writableDatabase
        val result = db.delete(TABLE_TODO, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return result
    }
}