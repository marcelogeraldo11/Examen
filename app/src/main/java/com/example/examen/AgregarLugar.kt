package com.example.examen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun AddPlaceScreen(
    onPlaceAdded: () -> Unit
) {
    // Define los campos del formulario y su estado
    var nombre by remember { mutableStateOf("") }
    var img by remember { mutableStateOf("") }
    var latitud by remember { mutableStateOf(0.0) }
    var longitud by remember { mutableStateOf(0.0) }
    var orden by remember { mutableStateOf(0) }
    var alojamiento by remember { mutableStateOf(0.0) }
    var traslado by remember { mutableStateOf(0.0) }
    var comentarios by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del lugar") }
        )
        // Agrega otros campos del formulario aquí
















        Button(
            onClick = {
                // Aquí puedes manejar la lógica para agregar el lugar
                // Llama a la función de devolución de llamada cuando se agrega el lugar
                onPlaceAdded()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Agregar Lugar")
        }
    }
}
