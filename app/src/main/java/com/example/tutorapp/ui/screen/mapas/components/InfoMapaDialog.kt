import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutorapp.R
import com.example.tutorapp.ui.screen.mapas.MapaViewModel
import com.example.tutorapp.ui.screen.mapas.components.ImagenCarruselMap
import com.example.tutorapp.ui.screen.mapas.components.ImagenFullScreen
import com.example.tutorapp.ui.theme.PrincipalGris
import com.example.tutorapp.ui.theme.RojoUES
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun InfoMapaDialog(
    onDismiss: () -> Unit,
    nombreUbicacion: String,
    descripcion: String,
    latUsuario: Double,
    longUsuario: Double,
    latUbicacion: Double,
    longUbicacion: Double,
    imagenes: List<String>,
    mapaViewModel: MapaViewModel = viewModel(),
    tipoUbicacion: String,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val ruta by mapaViewModel.ruta.collectAsState()
    var imagenSeleccionada by remember { mutableStateOf<String?>(null) }
    val isLoading by mapaViewModel.isLoading

    LaunchedEffect(ruta) {
        if (ruta != null) {
            sheetState.hide()
            onDismiss()
        }
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = nombreUbicacion,
                style = MaterialTheme.typography.headlineSmall.copy(color = RojoUES),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = tipoUbicacion,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    scope.launch {
                        mapaViewModel.cargarRuta(
                            origen = LatLng(latUsuario, longUsuario),
                            destino = LatLng(latUbicacion, longUbicacion),
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RojoUES
                )
            ) {
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_directions),
                        contentDescription = "Icono de ruta",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ruta")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            ImagenCarruselMap(imagenes = imagenes) { imagenUrl ->
                imagenSeleccionada = imagenUrl
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp),
                colors = CardDefaults.cardColors(containerColor = PrincipalGris),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = descripcion,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                    modifier = Modifier
                        .padding(16.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }

    // Opciones extras
    if (isLoading) {
        Dialog(onDismissRequest = {}) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = RojoUES)
            }
        }
    }

    // Dialogos
    if (imagenSeleccionada != null) {
        Dialog(onDismissRequest = { imagenSeleccionada = null }) {
            ImagenFullScreen(
                url = imagenSeleccionada!!,
                onClose = { imagenSeleccionada = null }
            )
        }
    }
}