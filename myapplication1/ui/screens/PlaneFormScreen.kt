package com.example.myapplication1.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication1.data.PlaneRepository
import com.example.myapplication1.model.Plane
import kotlinx.coroutines.launch
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.graphics.Color

@Composable
fun PlaneFormScreen(
    context: Context,
    planeToEdit: Plane?,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    val repository = remember(context) { PlaneRepository(context.applicationContext) }

    var model by remember { mutableStateOf(planeToEdit?.model ?: "") }
    var manufacturer by remember { mutableStateOf(planeToEdit?.manufacturer ?: "") }
    var year by remember { mutableStateOf(planeToEdit?.year?.toString() ?: "") }
    var capacity by remember { mutableStateOf(planeToEdit?.capacity?.toString() ?: "") }
    var type by remember { mutableStateOf(planeToEdit?.type ?: "") }

    var yearError by remember { mutableStateOf<String?>(null) }
    var capacityError by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = model,
            onValueChange = { model = it },
            label = { Text("Model") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = manufacturer,
            onValueChange = { manufacturer = it },
            label = { Text("Manufacturer") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = year,
            onValueChange = { year = it },
            label = { Text("Year") },
            isError = yearError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        yearError?.let {
            Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        OutlinedTextField(
            value = capacity,
            onValueChange = { capacity = it },
            label = { Text("Capacity") },
            isError = capacityError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        capacityError?.let {
            Text(it, color = Color.Red, style = MaterialTheme.typography.bodySmall)
        }

        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Type") },
            modifier = Modifier.fillMaxWidth()
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    yearError = null
                    capacityError = null
                    errorMessage = ""

                    val yearInt = year.toIntOrNull()
                    val capacityInt = capacity.toIntOrNull()
                    var isValid = true

                    if (model.isBlank() || manufacturer.isBlank() || year.isBlank() || capacity.isBlank() || type.isBlank()) {
                        errorMessage = "Усі поля мають бути заповнені!"
                        isValid = false
                    }

                    if (yearInt == null || yearInt < 1900 || yearInt > 2025) {
                        yearError = "Введіть коректний рік (1900–2025)"
                        isValid = false
                    }

                    if (capacityInt == null || capacityInt <= 0) {
                        capacityError = "Введіть коректну місткість (> 0)"
                        isValid = false
                    }

                    if (!isValid) return@Button

                    val plane = Plane(
                        id = planeToEdit?.id ?: 0,
                        model = model,
                        manufacturer = manufacturer,
                        year = yearInt!!,
                        capacity = capacityInt!!,
                        type = type
                    )

                    coroutineScope.launch {
                        if (planeToEdit == null) {
                            repository.insertPlane(plane)
                        } else {
                            repository.updatePlane(plane)
                        }
                        onSave()
                    }
                }
            ) {
                Text("Зберегти")
            }

            Button(onClick = onCancel) {
                Text("Скасувати")
            }
        }
    }
}
