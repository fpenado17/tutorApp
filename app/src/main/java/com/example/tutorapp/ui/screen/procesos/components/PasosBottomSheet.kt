package com.example.tutorapp.ui.screen.procesos.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.tutorapp.data.model.Paso
import com.example.tutorapp.ui.theme.PrincipalAqua
import com.example.tutorapp.ui.theme.PrincipalGris
import com.example.tutorapp.ui.theme.RojoUES
import com.example.tutorapp.ui.theme.SecundarioGris
import com.example.tutorapp.R
import com.example.tutorapp.ui.screen.mapas.components.ImagenFullScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PasoBottomSheet(
    paso: Paso,
    onCerrar: () -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    var imagenSeleccionada by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        stickyHeader {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(top = 16.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Cerrar",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clickable { onCerrar() }
                        .padding(end = 8.dp)
                )
                Text(
                    text = "Detalles",
                    style = MaterialTheme.typography.titleMedium.copy(MaterialTheme.colorScheme.primary)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${paso.orden} - ${paso.nombre}",
                style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Descripción:", style = MaterialTheme.typography.labelLarge)
            Text(paso.descripcion ?: "", style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))
        }

        if (paso.documento.isNotEmpty()) {
            item {
                Text("Documentos:", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(paso.documento.size) { index ->
                val documento = paso.documento[index]
                val backgroundColor = if (index % 2 == 0) PrincipalGris else SecundarioGris
                val hasUrl = documento.url.isNotBlank()

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
                            if (hasUrl) {
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
            item {
                Text("Ubicación:", style = MaterialTheme.typography.labelLarge)
                Text(paso.codigo_ubicacion, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(12.dp))

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
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Ver en el mapa",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        val costoNumero = paso.costo.toDoubleOrNull()
        if (costoNumero != null && costoNumero > 0.0) {
            item {
                Text("Costo:", style = MaterialTheme.typography.labelLarge)
                Text("$ ${paso.costo}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (paso.imagen.isNotEmpty()) {
            item {
                Text("Imágenes:", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(paso.imagen.size) { index ->
                val imagen = paso.imagen[index]
                Image(
                    painter = rememberAsyncImagePainter(imagen),
                    contentDescription = "Imagen $index",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { imagenSeleccionada = imagen },
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        if (!paso.url.isNullOrBlank()) {
            item {
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
                        Text(
                            "Abrir enlace principal",
                            style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }

    if (imagenSeleccionada != null) {
        Dialog(onDismissRequest = { imagenSeleccionada = null }) {
            ImagenFullScreen(
                url = imagenSeleccionada!!,
                onClose = { imagenSeleccionada = null }
            )
        }
    }
}