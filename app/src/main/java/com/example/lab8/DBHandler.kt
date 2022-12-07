package com.example.lab8

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.compose.ui.text.toLowerCase

class DBHandler
    (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + CONTACT_COL + " TEXT,"
                + DOB_COL + " TEXT)")

        db.execSQL(query)
    }

    fun addNewUser(
        Name: String?,
        Contact: String?,
        Dob: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME_COL, Name)
        values.put(CONTACT_COL, Contact)
        values.put(DOB_COL, Dob)

        db.insert(TABLE_NAME, null, values)

        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "lab8"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "lab8data"
        private const val ID_COL = "id"
        private const val NAME_COL = "name"
        private const val CONTACT_COL = "contact"
        private const val DOB_COL = "dob"
    }

    fun readUsers(): ArrayList<CourseModel> {
        val db = this.readableDatabase
        val cursorPeople: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val userModel: ArrayList<CourseModel> = ArrayList()

        if (cursorPeople.moveToFirst()) {
            do {
                userModel.add(
                    CourseModel(
                        cursorPeople.getString(1),
                        cursorPeople.getString(2),
                        cursorPeople.getString(3)
                    )
                )
            } while (cursorPeople.moveToNext())
        }

        cursorPeople.close()
        return userModel
    }

    fun removeUsers(user: String) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "name=?", arrayOf(user))
        db.close()
    }

    fun updateUser(
        originalUserName: String, name: String?, contact: String?,
        Dob: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NAME_COL, name)
        values.put(CONTACT_COL, contact)
        values.put(DOB_COL, Dob)

        db.update(TABLE_NAME, values, "name=?", arrayOf(originalUserName))
    }
}