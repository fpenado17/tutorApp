import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

data class BottomNavItem(val route: String, val icon: ImageVector, val label: String)

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("inicio", Icons.Default.Home, "Inicio"),
        BottomNavItem("procesos", Icons.Default.List, "Procesos"),
        BottomNavItem("mapas", Icons.Default.Place, "Mapa"),
        BottomNavItem("sesion", Icons.Default.Person, "Sesión"),
        BottomNavItem("informacion", Icons.Default.Info, "Más")
    )

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}