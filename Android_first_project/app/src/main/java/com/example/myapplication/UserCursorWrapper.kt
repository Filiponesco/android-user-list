package com.example.myapplication

import android.database.Cursor
import android.database.CursorWrapper

class UserCursorWrapper(cursor: Cursor?): CursorWrapper(cursor) {
    fun getUser(): User{
        var id = getInt(getColumnIndex(DBHelper.COLUMN_ID))
        var name = getString(getColumnIndex(DBHelper.COLUMN_NAME))
        var lastName = getString(getColumnIndex(DBHelper.COLUMN_LAST_NAME))

        var user = User(name, lastName, id)
        return user
    }
}
