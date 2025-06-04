package com.example.myapplication1.ui.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication1.model.Plane
import com.example.myapplication1.data.PlaneRepository
import kotlinx.coroutines.launch

@Composable
fun PlaneListScreen(
    context: Context,
    onEditPlane: (Plane) -> Unit,
    onAddPlane: () -> Unit
) {
    val repository = remember { PlaneRepository(context) }
    var planes by remember { mutableStateOf<List<Plane>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    // Завантаження літаків при запуску
    LaunchedEffect(Unit) {
        planes = repository.getAllPlanes()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Список літаків", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onAddPlane, modifier = Modifier.fillMaxWidth()) {
            Text("➕ Додати літак")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(planes) { plane ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onEditPlane(plane) },
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Модель: ${plane.model}")
                        Text("Виробник: ${plane.manufacturer}")
                        Text("Рік: ${plane.year}")
                        Text("Місткість: ${plane.capacity}")
                        Text("Тип: ${plane.type}")

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Button(onClick = { onEditPlane(plane) }) {
                                Text("Редагувати")
                            }
                            Button(onClick = {
                                coroutineScope.launch {
                                    repository.deletePlane(plane.id)
                                    planes = repository.getAllPlanes()
                                }
                            }) {
                                Text("Видалити")
                            }
                        }
                    }
                }
            }
        }
    }
}
