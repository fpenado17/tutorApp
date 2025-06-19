package com.example.tutorapp.ui.screen.mapas.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.example.tutorapp.data.model.MapaItem
import com.example.tutorapp.ui.screen.mapas.MapaViewModel
import com.example.tutorapp.R
import com.example.tutorapp.ui.theme.PrincipalAqua
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusquedaBottomSheet(
    listaUbicaciones: List<MapaItem>,
    onUbicacionSeleccionada: (MapaItem) -> Unit,
    onCerrar: () -> Unit,
    viewModel: MapaViewModel,
    onClickMic: () -> Unit
) {
    val query by viewModel.textoBusqueda.collectAsState()
    val listaFiltrada = remember(query, listaUbicaciones) {
        if (query.isBlank()) listaUbicaciones
        else listaUbicaciones.filter {
            it.nombre.contains(query, ignoreCase = true) ||
                    it.informacion.contains(query, ignoreCase = true)
        }
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.9f)
        .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { nuevoTexto ->
                    viewModel.busquedaMapa(nuevoTexto)
                },
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp),
                placeholder = { Text("Buscar...", style = MaterialTheme.typography.bodySmall) },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodySmall,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = PrincipalAqua,
                    unfocusedIndicatorColor = PrincipalAqua,
                    cursorColor = PrincipalAqua,
                    focusedPlaceholderColor = Color.Gray,
                    unfocusedPlaceholderColor = Color.Gray,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar",
                        tint = PrincipalAqua
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { viewModel.busquedaMapa("") }) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "Limpiar",
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.busquedaMapa(query)
                })
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onClickMic
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_outline_mic),
                    contentDescription = "Voz",
                    tint = PrincipalAqua
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(listaFiltrada) { ubicacion ->
                val pagerState = rememberPagerState()

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .clickable {
                            viewModel.busquedaMapa(ubicacion.nombre)
                            onUbicacionSeleccionada(ubicacion)
                            onCerrar()
                        }
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    HorizontalPager(
                        count = ubicacion.imagenes.size,
                        state = pagerState,
                        modifier = Modifier.fillMaxSize()
                    ) { page ->
                        val imageUrl = ubicacion.imagenes[page]
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "Imagen de fondo",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),

                        )
                    }

                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(8.dp),
                        activeColor = Color.White,
                        inactiveColor = Color.White.copy(alpha = 0.3f)
                    )

                    val isDarkTheme = isSystemInDarkTheme()
                    val tieneImagen = ubicacion.imagenes.any { it.isNotBlank() }

                    val textoColor = if (isDarkTheme) {
                        if (tieneImagen) Color.Black else Color.White
                    } else {
                        Color.White
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                            .padding(16.dp),
                        verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                    ) {
                        Text(
                            text = ubicacion.nombre,
                            style = MaterialTheme.typography.titleMedium,
                            color = textoColor
                        )
                        Text(
                            text = ubicacion.tipo,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = textoColor
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}
