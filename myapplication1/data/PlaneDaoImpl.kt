package com.example.myapplication1.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.myapplication1.model.Plane

class PlaneDaoImpl(private val db: SQLiteDatabase) : PlaneDao {

    override fun insertPlane(plane: Plane) {
        val values = ContentValues().apply {
            put("model", plane.model)
            put("manufacturer", plane.manufacturer)
            put("year", plane.year)
            put("capacity", plane.capacity)
            put("type", plane.type)
        }

        val rowId = db.insert("planes", null, values)
        Log.d("PlaneDaoImpl", "Inserted plane with id: $rowId")
    }

    override fun getAllPlanes(): List<Plane> {
        val planes = mutableListOf<Plane>()
        val cursor = db.query("planes", null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val model = getString(getColumnIndexOrThrow("model"))
                val manufacturer = getString(getColumnIndexOrThrow("manufacturer"))
                val year = getInt(getColumnIndexOrThrow("year"))
                val capacity = getInt(getColumnIndexOrThrow("capacity"))
                val type = getString(getColumnIndexOrThrow("type"))

                planes.add(Plane(id, model, manufacturer, year, capacity, type))
            }
            close()
        }

        return planes
    }

    override fun updatePlane(plane: Plane) {
        val values = ContentValues().apply {
            put("model", plane.model)
            put("manufacturer", plane.manufacturer)
            put("year", plane.year)
            put("capacity", plane.capacity)
            put("type", plane.type)
        }

        db.update("planes", values, "id = ?", arrayOf(plane.id.toString()))
    }

    override fun deletePlane(id: Int) {
        db.delete("planes", "id = ?", arrayOf(id.toString()))
    }
}
