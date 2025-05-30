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
import androidx.compose.foundation.ExperimentalFoundationApi
import com.example.tutorapp.ui.screen.inicio.components.ImagenCarrusel
import com.example.tutorapp.ui.screen.inicio.components.TarjetasCarrusel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InicioScreen() {
    val listaCarruselInicio = listOf(
        CarouselItem(
            "https://web.ues.edu.sv/wp-content/uploads/sites/20/2021/06/BANNER-PAGINA-WEB-UES.jpg",
            "Conoce los pasos a seguir del proceso universitario de 2022"
        ),
        CarouselItem(
            "https://lapagina.com.sv/wp-content/uploads/2022/05/UES-oficial.jpg",
            "Dirigido a todas las personas interesadas en participar del Proceso de Ingreso..."
        ),
        CarouselItem(
            "https://eluniversitario.ues.edu.sv/wp-content/uploads/sites/11/2020/07/001.jpg",
            "Se espera que participen más de 30,000 estudiantes"
        )
    )

    val listaCarruselProcesos = listOf(
        CarouselItem(null, "Lorem ipsum", "Lorem ipsum dolor sit amet..."),
        CarouselItem(null, "Cum sociis", "Cum sociis natoque penatibus..."),
        CarouselItem(null, "Euismod lacinia", "Euismod lacinia at quis risus..."),
    )

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            "Carrusel Inicio",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
        ImagenCarrusel(listaCarruselInicio)


        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Carrusel Procesos",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )
        TarjetasCarrusel(listaCarruselProcesos)
    }
}