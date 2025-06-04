package com.example.myapplication1.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication1.data.PlaneDao
import com.example.myapplication1.data.PlaneDaoImpl

class AppDatabase private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "airline.db"
        private const val DATABASE_VERSION = 1

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: AppDatabase(context.applicationContext).also { instance = it }
            }
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Таблиця "planes"
        db.execSQL("""
            CREATE TABLE planes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                model TEXT NOT NULL,
                manufacturer TEXT NOT NULL,
                year INTEGER NOT NULL,
                capacity INTEGER NOT NULL,
                type TEXT NOT NULL
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // У разі оновлення
        db.execSQL("DROP TABLE IF EXISTS planes")
        onCreate(db)
    }

    fun planeDao(): PlaneDao {
        return PlaneDaoImpl(readableDatabase)
    }
}
