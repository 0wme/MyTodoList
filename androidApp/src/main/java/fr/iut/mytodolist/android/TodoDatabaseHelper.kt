package fr.iut.mytodolist.android

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.provider.BaseColumns

class TodoDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todolist.db"
        private const val DATABASE_VERSION = 3 // Augmentez la version de la base de données
    }

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${TodoContract.TodoEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${TodoContract.TodoEntry.COLUMN_TODO} TEXT," +
                    "${TodoContract.TodoEntry.COLUMN_COLOR} INTEGER," +
                    "${TodoContract.TodoEntry.COLUMN_APPROVED} INTEGER)" // Créez la nouvelle colonne

        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${TodoContract.TodoEntry.TABLE_NAME}")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${TodoContract.TodoEntry.TABLE_NAME}")
        onCreate(db)
    }

    fun updateItem(id: Int, newValue: String, newColor: Int, newApproved: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TodoContract.TodoEntry.COLUMN_TODO, newValue)
        contentValues.put(TodoContract.TodoEntry.COLUMN_COLOR, newColor) // Mettez à jour la couleur
        contentValues.put(TodoContract.TodoEntry.COLUMN_APPROVED, newApproved) // Mettez à jour l'état d'approbation
        db.update(TodoContract.TodoEntry.TABLE_NAME, contentValues, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }

    fun deleteItem(id: Int) {
        val db = this.writableDatabase
        db.delete(TodoContract.TodoEntry.TABLE_NAME, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }
}