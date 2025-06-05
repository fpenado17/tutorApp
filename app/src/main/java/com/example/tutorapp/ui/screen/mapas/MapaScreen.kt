package com.example.tutorapp.ui.screen.mapas

import InfoMapaDialog
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutorapp.R
import com.example.tutorapp.data.model.MapaItem
import com.example.tutorapp.ui.screen.mapas.components.InfoMapaRuta
import com.google.android.gms.location.LocationServices
import com.example.tutorapp.ui.screen.mapas.components.RenderMapa

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaScreen() {
    // Variables
    val universidad = LatLng(13.715906106083413, -89.20337051153183)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(13.715906106083413, -89.20337051153183), // UES
            16f
        )
    }
    var animarCamara by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val viewModel: MapaViewModel = viewModel()
    val ubicaciones by viewModel.marcadores.collectAsState()
    var ubicacionSeleccionada by remember { mutableStateOf<MapaItem?>(null) }
    var mostrarInfoMapaDialog by remember { mutableStateOf(false) }
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val rutaSteps = viewModel.rutaSteps
    val mostrarRuta by viewModel.mostrarRuta
    val rutaResultado by viewModel.ruta.collectAsState()

    // Efectos
    LaunchedEffect(animarCamara) {
        if (animarCamara) {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(universidad, 16f),
                durationMs = 1000
            )
            animarCamara = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.cargarMarcadores()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    userLocation = LatLng(location.latitude, location.longitude)
                }
            }
        } catch (e: SecurityException) {
            // Maneja el caso en que los permisos no han sido concedidos
        }
    }

    // Contenido
    Box(modifier = Modifier.fillMaxSize()) {
        // Mapa
        RenderMapa(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            context = context,
            ubicaciones = ubicaciones,
            mostrarRuta = mostrarRuta,
            rutaSteps = rutaSteps,
            onMarkerClick = { ubicacion ->
                ubicacionSeleccionada = ubicacion
                mostrarInfoMapaDialog = true
            }
        )

        // Opciones extras
        FloatingActionButton(
            onClick = { animarCamara = true },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(0.dp, 10.dp, 80.dp, 0.dp)
                .size(42.dp),
            containerColor = Color.White.copy(alpha = 0.8f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_school),
                contentDescription = "Ir a la UES",
                tint = Color.Black,
                modifier = Modifier.size(22.dp)
            )
        }

        if (mostrarRuta && rutaResultado != null) {
            InfoMapaRuta(
                rutaResultado = rutaResultado!!,
                modifier = Modifier.align(Alignment.TopStart)
            )
        }
    }

    // Dialogos
    if (mostrarInfoMapaDialog && ubicacionSeleccionada != null && userLocation != null) {
        val ubicacion = ubicacionSeleccionada!!

        InfoMapaDialog(
            onDismiss = {
                mostrarInfoMapaDialog = false
                ubicacionSeleccionada = null
            },
            nombreUbicacion = ubicacion.nombre,
            descripcion = ubicacion.informacion,
            latUsuario = userLocation!!.latitude,
            longUsuario = userLocation!!.longitude,
            latUbicacion = ubicacion.latitud,
            longUbicacion = ubicacion.longitud,
            imagenes = ubicacion.imagenes,
            mapaViewModel = viewModel,
            tipoUbicacion = ubicacion.tipo,
        )
    }
}
