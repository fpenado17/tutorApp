package com.example.tutorapp.ui.screen.inicio

import AppTopBar
import BottomNavBar
import InicioScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tutorapp.ui.screen.informacion.InformacionScreen
import com.example.tutorapp.ui.screen.mapas.MapaScreenConPermisos
import com.example.tutorapp.ui.screen.procesos.DetalleProcesoScreen
import com.example.tutorapp.ui.screen.procesos.ProcesoScreen
import com.example.tutorapp.ui.screen.sesion.SesionScreen

@Composable
fun AppMainContent() {
    val navController = rememberNavController()
    val selectedRootRoute = remember { mutableStateOf("inicio") }
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry.value?.destination?.route

    val showBottomBar = currentRoute?.startsWith("detalle_proceso") != true &&
            currentRoute?.startsWith("mapa/") != true

    Scaffold(
        topBar = {
            AppTopBar()
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(
                    rootRoute = selectedRootRoute.value,
                    onRootRouteSelected = { route ->
                        selectedRootRoute.value = route
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("inicio") { InicioScreen(navController) }
            composable("procesos") { ProcesoScreen(navController) }
            composable("mapas") { MapaScreenConPermisos() }
            //composable("sesion") {
            //    if (sesionIniciada(LocalContext.current)) UsuarioScreen() // UsuarioScreen()
            //    else InicioSesionScreen() // InicioSesionScreen()
            // }
            composable("sesion") { SesionScreen() }

            composable("informacion") { InformacionScreen() }
            composable("detalle_proceso/{codigo}/{porFacultad}") { backStackEntry ->
                val codigo = backStackEntry.arguments?.getString("codigo") ?: ""
                val porFacultad = backStackEntry.arguments?.getString("porFacultad")?.toBoolean() ?: false
                DetalleProcesoScreen(
                    codigo = codigo,
                    porFacultad = porFacultad,
                    onBack = { navController.popBackStack() },
                    navController = navController
                )
            }
            composable("mapa/{codigoUbicacion}?back={back}") { backStackEntry ->
                val codigoUbicacion = backStackEntry.arguments?.getString("codigoUbicacion")
                val mostrarBotonBack = backStackEntry.arguments?.getString("back")?.toBoolean() ?: false

                MapaScreenConPermisos(
                    codigoUbicacion = codigoUbicacion,
                    mostrarBotonBack = mostrarBotonBack,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}