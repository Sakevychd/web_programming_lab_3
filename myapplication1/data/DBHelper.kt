package com.example.myapplication1.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication1.model.Plane

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "airline.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_PLANES = "planes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MODEL = "model"
        private const val COLUMN_MANUFACTURER = "manufacturer"
        private const val COLUMN_YEAR = "year"
        private const val COLUMN_CAPACITY = "capacity"
        private const val COLUMN_TYPE = "type"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_PLANES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_MODEL TEXT NOT NULL,
                $COLUMN_MANUFACTURER TEXT NOT NULL,
                $COLUMN_YEAR INTEGER NOT NULL,
                $COLUMN_CAPACITY INTEGER NOT NULL,
                $COLUMN_TYPE TEXT NOT NULL
            )
        """.trimIndent()
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PLANES")
        onCreate(db)
    }

    fun getAllPlanes(): List<Plane> {
        val planes = mutableListOf<Plane>()
        val db = readableDatabase
        val cursor = db.query(TABLE_PLANES, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val plane = Plane(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                model = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MODEL)),
                manufacturer = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MANUFACTURER)),
                year = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_YEAR)),
                capacity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CAPACITY)),
                type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
            )
            planes.add(plane)
        }

        cursor.close()
        db.close()
        return planes
    }

    fun deletePlane(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_PLANES, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun addPlane(plane: Plane): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MODEL, plane.model)
            put(COLUMN_MANUFACTURER, plane.manufacturer)
            put(COLUMN_YEAR, plane.year)
            put(COLUMN_CAPACITY, plane.capacity)
            put(COLUMN_TYPE, plane.type)
        }
        val result = db.insert(TABLE_PLANES, null, values)
        db.close()
        return result
    }

    fun updatePlane(plane: Plane): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_MODEL, plane.model)
            put(COLUMN_MANUFACTURER, plane.manufacturer)
            put(COLUMN_YEAR, plane.year)
            put(COLUMN_CAPACITY, plane.capacity)
            put(COLUMN_TYPE, plane.type)
        }
        val result = db.update(TABLE_PLANES, values, "$COLUMN_ID = ?", arrayOf(plane.id.toString()))
        db.close()
        return result
    }
}
