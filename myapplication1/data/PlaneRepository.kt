package com.example.myapplication1.data

import android.content.Context
import com.example.myapplication1.data.database.AppDatabase
import com.example.myapplication1.model.Plane
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlaneRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).planeDao()

    suspend fun getAllPlanes(): List<Plane> = withContext(Dispatchers.IO) {
        dao.getAllPlanes()
    }

    suspend fun deletePlane(id: Int) = withContext(Dispatchers.IO) {
        dao.deletePlane(id)
    }

    suspend fun insertPlane(plane: Plane) = withContext(Dispatchers.IO) {
        dao.insertPlane(plane)
    }

    suspend fun updatePlane(plane: Plane) = withContext(Dispatchers.IO) {
        dao.updatePlane(plane)
    }
}
