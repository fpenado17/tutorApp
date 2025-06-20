package com.example.tutorapp.ui.screen.informacion

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutorapp.ui.screen.informacion.components.TarjetaInformacion
import com.example.tutorapp.ui.screen.procesos.components.SimpleList
import com.example.tutorapp.ui.theme.PrincipalAqua
import com.example.tutorapp.ui.screen.informacion.components.DetalleBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformacionScreen() {
    val viewModel: InformacionViewModel = viewModel()
    val seccionSeleccionado by viewModel.seccionSeleccionado.collectAsState()
    val infoItems by viewModel.infoItems.collectAsState()
    var mostrarBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val infoCargando by viewModel.infoCargando.collectAsState()
    val infoTimeoutReached by viewModel.infoTimeoutReached.collectAsState()
    val detalleSeleccionado by viewModel.detalleSeleccionado.collectAsState()


    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {

            if(seccionSeleccionado == null){
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f)) {
                        TarjetaInformacion(
                            nombre = "Contactos",
                            icono = Icons.Filled.AccountCircle,
                            onClick = {
                                viewModel.seleccionarSecccion("Contactos")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        TarjetaInformacion(
                            nombre = "Sitios oficiales",
                            icono = Icons.Filled.Share,
                            onClick = {
                                viewModel.seleccionarSecccion("Paginas")
                            }
                        )
                    }
                }
            } else {
                SimpleList(
                    small = true,
                    items = infoItems,
                    isLoading = infoCargando,
                    timeoutReached = infoTimeoutReached,
                    emptyMessage = "No se encontraron registros.",
                    onItemClick = {info ->
                        mostrarBottomSheet = true
                        viewModel.seleccionarDetalle(info)
                    }
                ) { info ->
                    Column {
                        Text(info.nombre, style = MaterialTheme.typography.titleMedium, color = Color.White)
                    }
                }
            }
        }
        if (seccionSeleccionado != null) {
            FloatingActionButton(
                containerColor = Color.White.copy(alpha = 0.9f),
                onClick = { viewModel.limpiarSeleccion() },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 12.dp, bottom = 10.dp)
                    .size(56.dp)
                    .border(
                        width = 1.dp,
                        color = PrincipalAqua,
                        shape = CircleShape
                    )
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

    if (mostrarBottomSheet && detalleSeleccionado != {}) {
        ModalBottomSheet(
            onDismissRequest = { mostrarBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            DetalleBottomSheet(detalleSeleccionado)
        }
    }
}