package com.example.tutorapp.ui.screen.mapas.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tutorapp.data.model.RutaResultado
import com.example.tutorapp.ui.theme.PrincipalAqua

@Composable
fun InfoMapaRuta(rutaResultado: RutaResultado, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(50.dp, 8.dp, 12.dp, 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f)),
        border = BorderStroke(1.dp, PrincipalAqua)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                Text(
                    text = "Distancia: ",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = rutaResultado.distancia,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
            Row {
                Text(
                    text = "Duración aprox: ",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = rutaResultado.duracion,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
            Row {
                Text(
                    text = "Caminando",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
            }
        }
    }
}