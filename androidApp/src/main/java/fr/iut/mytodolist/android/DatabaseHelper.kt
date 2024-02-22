package fr.iut.mytodolist.android

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TodoDatabase"
        private const val TABLE_NAME = "TodoTable"
        private const val KEY_ID = "id"
        private const val KEY_TODO = "todo"
        private const val KEY_DATETIME = "datetime"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TODO + " TEXT,"
                + KEY_DATETIME + " TEXT" + ")")
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addTodo(todo: String, dateTime: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_TODO, todo)
        contentValues.put(KEY_DATETIME, dateTime)
        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getTodos(): List<Pair<String, String>> {
        val todoList = ArrayList<Pair<String, String>>()
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val todo = cursor.getString(cursor.getColumnIndex(KEY_TODO))
                val dateTime = cursor.getString(cursor.getColumnIndex(KEY_DATETIME))
                todoList.add(Pair(todo, dateTime))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return todoList
    }

    fun deleteTodo(id: Int): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, id)
        val success = db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(id.toString()))
        db.close()
        return success
    }
}