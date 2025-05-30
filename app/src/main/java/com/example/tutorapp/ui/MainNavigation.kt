package com.example.tutorapp.ui.screen.inicio

import BottomNavBar
import InicioScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppMainContent() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("inicio") { InicioScreen() }
            composable("procesos") { InicioScreen() } // ProcesoScreen()
            composable("mapas") { InicioScreen() } // MapaScreen()
            //composable("sesion") {
            //    if (sesionIniciada(LocalContext.current)) UsuarioScreen() // UsuarioScreen()
            //    else InicioSesionScreen() // InicioSesionScreen()
            // }
            composable("sesion") { InicioScreen()}
            composable("informacion") { InicioScreen() } // InformacionScreen()
        }
    }
}