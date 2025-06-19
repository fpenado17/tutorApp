package com.example.tutorapp.ui.screen.procesos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tutorapp.data.model.Paso
import com.example.tutorapp.ui.components.InfoTag
import com.example.tutorapp.ui.theme.PrincipalAqua
import com.example.tutorapp.ui.theme.PrincipalGris
import com.example.tutorapp.ui.theme.SecundarioGris

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasoList(
    pasos: List<Paso>,
    modifier: Modifier = Modifier,
    onPasoClick: (Paso) -> Unit
) {
    Column(modifier = modifier) {
        pasos.forEachIndexed { index, paso ->
            val backgroundColor = if (index % 2 == 0) PrincipalGris else SecundarioGris

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(backgroundColor)
                    .clickable { onPasoClick(paso) }
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "${paso.orden} - ${paso.nombre}",
                        style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if (!paso.descripcion.isNullOrBlank()) {
                        Text(
                            text = paso.descripcion,
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                    ) {
                        if (!paso.url.isNullOrBlank()) {
                            InfoTag(text = "URL", fondo = PrincipalAqua, color = Color.White)
                        }

                        if (!paso.documento.isNullOrEmpty()) {
                            InfoTag(text = "Documentos: ${paso.documento.size}", fondo = PrincipalAqua, color = Color.White)
                        }

                        if (paso.costo.toDoubleOrNull() != null && paso.costo.toDouble() > 0.0) {
                            InfoTag(text = "Costo: ${paso.costo}", fondo = PrincipalAqua, color = Color.White)
                        }

                        if (!paso.codigo_ubicacion.isNullOrBlank()) {
                            InfoTag(text = "Ubicación", fondo = PrincipalAqua, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

