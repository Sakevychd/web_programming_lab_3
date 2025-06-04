package com.example.myapplication1.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlaneDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE $TABLE_PLANES (" +
                    "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_MODEL TEXT," +
                    "$COL_MANUFACTURER TEXT," +
                    "$COL_CAPACITY INTEGER," +
                    "$COL_YEAR INTEGER," +
                    "$COL_RANGE INTEGER)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PLANES")
        onCreate(db)
    }

    companion object {
        const val DB_NAME = "planes.db"
        const val DB_VERSION = 1

        const val TABLE_PLANES = "planes"
        const val COL_ID = "id"
        const val COL_MODEL = "model"
        const val COL_MANUFACTURER = "manufacturer"
        const val COL_CAPACITY = "capacity"
        const val COL_YEAR = "year"
        const val COL_RANGE = "range_km"
    }
}
