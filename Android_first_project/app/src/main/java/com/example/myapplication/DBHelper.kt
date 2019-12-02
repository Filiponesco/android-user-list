package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DB_NAME, factory, VERSION){
    companion object{
        const val DB_NAME = "users.Db"
        const val VERSION = 1
        const val TABLE_NAME = "Users"
        const val COLUMN_ID = "UserID"
        const val COLUMN_NAME = "UserName"
        const val COLUMN_LAST_NAME = "UserLastName"
        private fun contentValues(user: User?): ContentValues{
            val values = ContentValues()
            values.put(COLUMN_NAME, user?.firstName)
            values.put(COLUMN_LAST_NAME, user?.lastName)

            return values
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PRODUCTS_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "( "
                + COLUMN_ID +" integer primary key autoincrement, "+
                COLUMN_NAME + ", " +
                COLUMN_LAST_NAME +
                ")")
        db.execSQL(CREATE_PRODUCTS_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }
    fun addUser(user: User) {
        var values = Companion.contentValues(user)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }
    private fun queryUsers(): UserCursorWrapper {
        val db = this.readableDatabase
        var rawQuery = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        return UserCursorWrapper(rawQuery)
    }
    private fun queryUser(id: Int): UserCursorWrapper{
        val db = this.readableDatabase
        var rawQuery = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $id", null)
        return UserCursorWrapper(rawQuery)
    }
    fun getAllUsers(): MutableList<User>{
        var cursor = queryUsers()
        var users = mutableListOf<User>()
        try{
            cursor.moveToFirst()
            while(!cursor.isAfterLast){
                users.add(cursor.getUser())
                cursor.moveToNext()
            }
        } catch(e: Exception){
            //
        }
        finally{
            cursor.close()
        }
        return users
    }
    fun getUser(id: Int): User?{
        var cursor = queryUser(id)
        try{
            if(cursor.count == 0)
            {
                return null
            }
            cursor.moveToFirst()
            return cursor.getUser()
        } finally{
            cursor.close()
        }
    }
    fun update(user: User?): Boolean{
        val db = this.writableDatabase
        val values = Companion.contentValues(user)
        val success = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(user?.id.toString())) //czemu to działa?
        db.close()
        return Integer.parseInt("$success") != -1
    }
    fun delete(user: User?): Boolean{
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(user?.id.toString()))
        db.close()
        return Integer.parseInt("$success") != -1
    }
}