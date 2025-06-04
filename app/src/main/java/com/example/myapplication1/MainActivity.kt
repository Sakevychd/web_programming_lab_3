package com.example.myapplication1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.myapplication1.model.Plane
import com.example.myapplication1.ui.screens.PlaneFormScreen
import com.example.myapplication1.ui.screens.PlaneListScreen
import com.example.myapplication1.ui.theme.MyApplication1Theme
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate called")

        setContent {
            MyApplication1Theme {
                val navController = rememberNavController()
                var selectedPlane by remember { mutableStateOf<Plane?>(null) }

                NavHost(navController, startDestination = "plane_list") {
                    composable("plane_list") {
                        PlaneListScreen(
                            context = applicationContext,
                            onAddPlane = {
                                selectedPlane = null
                                navController.navigate("plane_form")
                            },
                            onEditPlane = {
                                selectedPlane = it
                                navController.navigate("plane_form")
                            }
                        )
                    }

                    composable("plane_form") {
                        PlaneFormScreen(
                            context = applicationContext,
                            planeToEdit = selectedPlane,
                            onSave = { navController.popBackStack() },
                            onCancel = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
