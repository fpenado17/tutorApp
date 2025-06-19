import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tutorapp.ui.screen.inicio.InicioViewModel
import com.example.tutorapp.ui.screen.inicio.components.ImagenCarrusel
import com.example.tutorapp.ui.screen.inicio.components.TarjetasCarrusel
import kotlinx.coroutines.delay
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun InicioScreen(
    navController: NavController,
    viewModel: InicioViewModel = viewModel(),
) {
    val generalItems = viewModel.generalItems.collectAsState()
    val procesosItems = viewModel.procesosItems.collectAsState()

    var generalTimeoutReached by remember { mutableStateOf(false) }
    var procesosTimeoutReached by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getInformacionPorTipo("General")
        viewModel.getInformacionProceso(10)
    }

    LaunchedEffect(generalItems.value) {
        if (generalItems.value.isEmpty()) {
            delay(5000)
            if (generalItems.value.isEmpty()) generalTimeoutReached = true
        }
    }

    LaunchedEffect(procesosItems.value) {
        if (procesosItems.value.isEmpty()) {
            delay(5000)
            if (procesosItems.value.isEmpty()) procesosTimeoutReached = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            "Bienvenido a TutorApp",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(14.dp)
                .align(Alignment.CenterHorizontally)
        )
        when {
            generalItems.value.isNotEmpty() -> ImagenCarrusel(generalItems.value)
            generalTimeoutReached -> Text(
                "No se encontraron registros generales.",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            else -> CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }


        Spacer(modifier = Modifier.height(10.dp))

        Text(
            "Procesos más buscados",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(14.dp)
        )
        when {
            procesosItems.value.isNotEmpty() -> TarjetasCarrusel(
                procesosItems.value,
                onProcesoClick = { proceso ->
                    navController.navigate("detalle_proceso/${proceso.codigo}/${proceso.por_facultad}")
                }
            )
            procesosTimeoutReached -> Text(
                "No se encontraron procesos.",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 14.dp)
                    .align(Alignment.CenterHorizontally)
            )
            else -> CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}