package com.example.tutorapp.ui.screen.mapas.components

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.tutorapp.ui.theme.PrincipalGris
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.tutorapp.data.model.CatTipoUbicacion
import com.example.tutorapp.ui.theme.GrisClaro
import com.example.tutorapp.ui.theme.PrincipalAqua
import com.example.tutorapp.ui.theme.RojoUES
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FiltroBottomSheet(
    onAplicar: (List<String>) -> Unit,
    onCerrar: () -> Unit,
    filtrosSeleccionados: List<String>,
    catTipoUbicaciones: List<CatTipoUbicacion>
) {
    val filtrosSeleccionadosTemp = remember { mutableStateListOf<String>() }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(filtrosSeleccionados) {
        filtrosSeleccionadosTemp.clear()
        filtrosSeleccionadosTemp.addAll(filtrosSeleccionados)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Filtros",
                style = MaterialTheme.typography.headlineSmall.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(catTipoUbicaciones) { opcion ->
                    val nombre = opcion.nombre
                    val seleccionado = nombre in filtrosSeleccionadosTemp
                    val chipColor = if (seleccionado) PrincipalAqua else PrincipalGris
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .background(color = chipColor, shape = RoundedCornerShape(16.dp))
                            .clickable {
                                if (seleccionado) {
                                    if (filtrosSeleccionadosTemp.size > 1) {
                                        filtrosSeleccionadosTemp.remove(nombre)
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Debe haber al menos un filtro seleccionado.")
                                        }
                                    }
                                } else {
                                    filtrosSeleccionadosTemp.add(nombre)
                                }
                            }
                            .padding(vertical = 8.dp, horizontal = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = nombre,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        filtrosSeleccionadosTemp.clear()
                        filtrosSeleccionadosTemp.add("Facultad")
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = GrisClaro)
                ) {
                    Text("Limpiar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        onAplicar(filtrosSeleccionadosTemp.toList())
                        onCerrar()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = RojoUES)
                ) {
                    Text(
                        "Aplicar",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
