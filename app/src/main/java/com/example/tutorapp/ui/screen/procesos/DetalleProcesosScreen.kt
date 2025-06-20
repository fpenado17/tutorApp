package com.example.tutorapp.ui.screen.procesos

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.tutorapp.data.model.Paso
import com.example.tutorapp.ui.screen.procesos.components.ListFacultades
import com.example.tutorapp.ui.screen.procesos.components.ListProcesoDetalle
import com.example.tutorapp.ui.screen.procesos.components.PasoBottomSheet
import com.example.tutorapp.ui.theme.PrincipalAqua

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleProcesoScreen(
    codigo: String,
    porFacultad: Boolean,
    onBack: () -> Unit,
    navController: NavController
) {
    val viewModel: DetalleProcesoViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return DetalleProcesoViewModel(codigo, porFacultad) as T
        }
    })

    val detalles by viewModel.detalle.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val facultades by viewModel.facultades.collectAsState()
    val facultadSeleccionada by viewModel.facultadSeleccionada.collectAsState()

    var mostrarBottomSheet by remember { mutableStateOf(false) }
    var pasoSeleccionado by remember { mutableStateOf<Paso?>(null) }


    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (porFacultad) {
                ListFacultades(
                    facultades = facultades,
                    facultadSeleccionada = facultadSeleccionada,
                    onSeleccionarFacultad = { codigo -> viewModel.seleccionarFacultad(codigo) }
                )
            }

            when {
                cargando -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                porFacultad && detalles.isEmpty() && facultadSeleccionada.isNullOrBlank() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Seleccione una facultad", color = MaterialTheme.colorScheme.primary)
                    }
                }
                detalles.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No se encontró información", color = MaterialTheme.colorScheme.primary)
                    }
                }
                else -> {
                    ListProcesoDetalle(
                        detalles = detalles,
                        onPasoClick = { paso ->
                            pasoSeleccionado = paso
                            mostrarBottomSheet = true
                        }
                    )
                }
            }
        }

        if (mostrarBottomSheet && pasoSeleccionado != null) {
            Dialog(
                onDismissRequest = { mostrarBottomSheet = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    PasoBottomSheet(
                        paso = pasoSeleccionado!!,
                        onCerrar = { mostrarBottomSheet = false },
                        navController = navController
                    )
                }
            }
        }

        FloatingActionButton(
            containerColor = Color.White.copy(alpha = 0.9f),
            onClick = { onBack() },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 12.dp, bottom = 10.dp)
                .size(56.dp)
                .border(1.dp, PrincipalAqua, CircleShape)
                .clip(CircleShape),
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Regresar",
                tint = Color.Black
            )
        }
    }
}