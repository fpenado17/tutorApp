package com.example.tutorapp.ui.screen.mapas.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.content.Context
import com.example.tutorapp.R
import com.example.tutorapp.common.decodePolyline
import com.example.tutorapp.common.getBitmapDescriptorFromVector
import com.example.tutorapp.data.model.MapaItem
import com.example.tutorapp.ui.theme.RojoUES
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberMarkerState
import com.google.android.gms.maps.model.MapStyleOptions

@Composable
fun RenderMapa(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    context: Context,
    ubicaciones: List<MapaItem>,
    mostrarRuta: Boolean,
    rutaSteps: List<String>,
    onMarkerClick: (MapaItem) -> Unit
) {
    val mapStyleOptions = remember {
        MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style_no_pois)
    }

    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isMyLocationEnabled = true,
            mapStyleOptions = mapStyleOptions
        ),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            mapToolbarEnabled = false
        )
    ) {
        ubicaciones.forEach { ubicacion ->
            val iconDescriptor = remember(ubicacion.icono) {
                getBitmapDescriptorFromVector(context, ubicacion.icono)
            }

            Marker(
                state = rememberMarkerState(
                    position = LatLng(ubicacion.latitud, ubicacion.longitud)
                ),
                title = ubicacion.nombre,
                snippet = ubicacion.informacion,
                icon = iconDescriptor ?: BitmapDescriptorFactory.defaultMarker(),
                onClick = {
                    onMarkerClick(ubicacion)
                    true
                }
            )
        }

        if (mostrarRuta) {
            val polylinePoints = rutaSteps.flatMap { decodePolyline(it) }

            LaunchedEffect(polylinePoints) {
                if (polylinePoints.isNotEmpty()) {
                    cameraPositionState.animate(
                        update = CameraUpdateFactory.newLatLngBounds(
                            LatLngBounds.builder().apply {
                                polylinePoints.forEach { point -> this.include(point) }
                            }.build(),
                            100
                        ),
                        durationMs = 1000
                    )
                }
            }

            Polyline(
                points = polylinePoints,
                color = RojoUES,
                width = 8f
            )
        }
    }
}