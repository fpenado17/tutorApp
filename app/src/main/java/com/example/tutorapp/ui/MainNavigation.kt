package com.example.tutorapp.ui.screen.inicio

import AppTopBar
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
import com.example.tutorapp.ui.screen.informacion.InformacionScreen
import com.example.tutorapp.ui.screen.mapas.MapaScreenConPermisos
import com.example.tutorapp.ui.screen.procesos.ProcesoScreen
import com.example.tutorapp.ui.screen.sesion.SesionScreen

@Composable
fun AppMainContent() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            AppTopBar()
        },
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
            composable("procesos") { ProcesoScreen() }
            composable("mapas") { MapaScreenConPermisos() }
            //composable("sesion") {
            //    if (sesionIniciada(LocalContext.current)) UsuarioScreen() // UsuarioScreen()
            //    else InicioSesionScreen() // InicioSesionScreen()
            // }
            composable("sesion") { SesionScreen() }
            composable("informacion") { InformacionScreen() }
        }
    }
}