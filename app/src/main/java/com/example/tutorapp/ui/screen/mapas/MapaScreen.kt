package com.example.tutorapp.ui.screen.mapas

import InfoMapaDialog
import android.app.Activity
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.tutorapp.common.startVoiceRecognition
import com.example.tutorapp.data.model.MapaItem
import com.example.tutorapp.ui.screen.mapas.components.BusquedaBar
import com.example.tutorapp.ui.screen.mapas.components.BusquedaBottomSheet
import com.example.tutorapp.ui.screen.mapas.components.InfoMapaRuta
import com.google.android.gms.location.LocationServices
import com.example.tutorapp.ui.screen.mapas.components.RenderMapa
import com.example.tutorapp.ui.theme.PrincipalAqua

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
    val textoBusqueda by viewModel.textoBusqueda.collectAsState()
    var mostrarDialogoBusqueda by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var ubicacionSeleccionadaZoom by remember { mutableStateOf<MapaItem?>(null) }
    val speechResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data
                ?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                ?.firstOrNull()

            spokenText?.let {
                viewModel.busquedaMapa(it)
                mostrarDialogoBusqueda = true
            }
        }
    }


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

    LaunchedEffect(ubicacionSeleccionadaZoom) {
        ubicacionSeleccionadaZoom?.let {
            val latLng = LatLng(it.latitud, it.longitud)
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(latLng, 19f),
                durationMs = 1000
            )
            ubicacionSeleccionadaZoom = null
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

        FloatingActionButton(
            onClick = { animarCamara = true },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp, 0.dp, 0.dp, 80.dp)
                .size(56.dp)
                .border(
                    width = 1.dp,
                    color = PrincipalAqua,
                    shape = CircleShape
                )
                .clip(CircleShape),
            containerColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = "Filtros",
                tint = Color.Black,

            )
        }

        BusquedaBar(
            textoBusqueda = textoBusqueda,
            onClick = {mostrarDialogoBusqueda = true },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            onClickMic = { startVoiceRecognition(context, speechResultLauncher) },
        )

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

    if (mostrarDialogoBusqueda) {
        ModalBottomSheet(
            onDismissRequest = { mostrarDialogoBusqueda = false },
            sheetState = sheetState
        ) {
            BusquedaBottomSheet(
                listaUbicaciones = ubicaciones,
                onUbicacionSeleccionada = { ubicacion ->
                    viewModel.busquedaMapa(ubicacion.nombre)
                    mostrarDialogoBusqueda = false
                    ubicacionSeleccionadaZoom = ubicacion
                },
                onCerrar = { mostrarDialogoBusqueda = false },
                viewModel = viewModel,
                onClickMic = { startVoiceRecognition(context, speechResultLauncher) }
            )
        }
    }

}
