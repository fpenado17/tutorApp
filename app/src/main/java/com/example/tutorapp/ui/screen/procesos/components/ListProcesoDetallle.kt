package com.example.tutorapp.ui.screen.procesos.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tutorapp.data.model.Paso
import com.example.tutorapp.data.model.ProcesoDetalle
import com.example.tutorapp.ui.theme.RojoUES

@Composable
fun ListProcesoDetalle(
    detalles: List<ProcesoDetalle>,
    onPasoClick: (Paso) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        detalles.forEach { detalle ->
            item {
                Text(
                    text = detalle.titulo,
                    style = MaterialTheme.typography.headlineSmall.copy(color = RojoUES),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
                Text(
                    text = detalle.descripcion ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )

                Spacer(Modifier.height(12.dp))

                PasoList(
                    pasos = detalle.pasos ?: emptyList(),
                    onPasoClick = onPasoClick
                )
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}