package com.example.tutorapp.ui.screen.procesos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tutorapp.data.model.Facultades
import com.example.tutorapp.ui.theme.RojoUES

@Composable
fun ListFacultades(
    facultades: List<Facultades>,
    facultadSeleccionada: String?,
    onSeleccionarFacultad: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        items(facultades.size) { index ->
            val facultad = facultades[index]
            val seleccionada = facultad.codigo == facultadSeleccionada
            val backgroundColor = if (seleccionada) RojoUES else Color.White
            val borderColor = if (seleccionada) MaterialTheme.colorScheme.primary else Color.Gray

            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .background(backgroundColor, shape = CircleShape)
                    .clip(CircleShape)
                    .border(2.dp, borderColor, CircleShape)
                    .clickable { onSeleccionarFacultad(facultad.codigo) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = facultad.nombre,
                    color = if (seleccionada) Color.White else Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
}