package com.example.myapplication1.data

import com.example.myapplication1.model.Plane

interface PlaneDao {
    fun insertPlane(plane: Plane)
    fun getAllPlanes(): List<Plane>
    fun updatePlane(plane: Plane)
    fun deletePlane(id: Int)
}

