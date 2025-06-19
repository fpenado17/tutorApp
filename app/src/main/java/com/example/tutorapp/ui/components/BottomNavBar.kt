import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.tutorapp.R
import com.example.tutorapp.ui.theme.PrincipalAqua
import com.example.tutorapp.ui.theme.PrincipalGris
import androidx.compose.ui.unit.dp

data class BottomNavItem(val route: String, val icon: Painter, val label: String, val iconSelect: Painter,)

@Composable
fun BottomNavBar(
    rootRoute: String,
    onRootRouteSelected: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem("inicio", painterResource(R.drawable.ic_inicio), "Inicio", painterResource(R.drawable.ic_inicio_seleccionado)),
        BottomNavItem("procesos", painterResource(R.drawable.ic_academico), "Procesos", painterResource(R.drawable.ic_academico_seleccionado)),
        BottomNavItem("mapas", painterResource(R.drawable.ic_mapa), "Mapa", painterResource(R.drawable.ic_mapa_seleccionado)),
        BottomNavItem("sesion", painterResource(R.drawable.ic_inicio_sesion), "Sesión", painterResource(R.drawable.ic_inicio_sesion_seleccionado)),
        BottomNavItem("informacion", painterResource(R.drawable.ic_otros), "Más", painterResource(R.drawable.ic_otros_seleccionado))
    )

    NavigationBar(
        containerColor = PrincipalGris
    ) {
        items.forEach { item ->
            val selected = rootRoute == item.route

            NavigationBarItem(
                icon = {
                    Image(
                        painter = if (selected) item.iconSelect else item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp),
                    )
                },
                label = {
                    if (selected) Text(text = item.label, color = White)
                },
                selected = selected,
                onClick = {
                    if (!selected) onRootRouteSelected(item.route)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = PrincipalGris,
                    selectedIconColor = PrincipalAqua,
                    unselectedIconColor = White,
                    selectedTextColor = White,
                    unselectedTextColor = White
                )
            )
        }
    }
}