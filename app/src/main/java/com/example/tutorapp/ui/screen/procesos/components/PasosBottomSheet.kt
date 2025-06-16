package com.example.tutorapp.ui.screen.procesos.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tutorapp.data.model.Paso
import com.example.tutorapp.ui.theme.PrincipalAqua
import com.example.tutorapp.ui.theme.PrincipalGris
import com.example.tutorapp.ui.theme.RojoUES
import com.example.tutorapp.ui.theme.SecundarioGris
import com.example.tutorapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasoBottomSheet(
    paso: Paso,
    onCerrar: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "${paso.orden} - ${paso.nombre}",
            style = MaterialTheme.typography.headlineSmall.copy(color = RojoUES),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Descripción:", style = MaterialTheme.typography.labelLarge)
        Text(paso.descripcion ?: "", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(8.dp))

        if (paso.documento.isNotEmpty()) {
            Text("Documentos:", style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(8.dp))

            paso.documento.forEachIndexed { index, documento ->
                val backgroundColor = if (index % 2 == 0) PrincipalGris else SecundarioGris

                val hasUrl = documento.url.isNotBlank()
                val context = LocalContext.current

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(backgroundColor)
                        .clickable(enabled = hasUrl) {
                            if (hasUrl) {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(documento.url))
                                context.startActivity(intent)
                            }
                        }
                        .padding(8.dp)
                ) {
                    Column {
                        Row {
                            Text(
                                text = documento.nombre,
                                style = MaterialTheme.typography.titleMedium.copy(color = Color.White)
                            )

                            if (documento.url.isNotBlank()) {
                                Text(
                                    text = " *",
                                    style = MaterialTheme.typography.titleMedium.copy(color = PrincipalAqua)
                                )
                            }
                        }

                        if (!documento.descripcion.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = documento.descripcion,
                                style = MaterialTheme.typography.bodySmall.copy(color = Color.White)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (!paso.codigo_ubicacion.isNullOrBlank()) {
            Text("Ubicación:", style = MaterialTheme.typography.labelLarge)
            Text(paso.codigo_ubicacion, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    navController.navigate("mapa/${paso.codigo_ubicacion}?back=true")
                    onCerrar()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = RojoUES)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mapa),
                    contentDescription = "Ubicación",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Ver en el mapa")
            }

            Spacer(modifier = Modifier.height(8.dp))
        }

        val costoNumero = paso.costo.toDoubleOrNull()
        if (costoNumero != null && costoNumero > 0.0) {
            Text("Costo:", style = MaterialTheme.typography.labelLarge)
            Text("$ ${paso.costo}" ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (!paso.url.isNullOrBlank()) {
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(paso.url))
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = RojoUES)
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = "Icono de enlace",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Abrir enlace principal")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}