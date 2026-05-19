package com.example.mapsynav.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsynav.ui.screens.HomeScreen
import com.example.mapsynav.ui.screens.MiUbicacionScreen
import com.example.mapsynav.ui.screens.PlazaScreen

// Rutas de la aplicación
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Plaza : Screen("plaza")
    object MiUbicacion : Screen("mi_ubicacion")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Host de navegación que maneja el cambio entre pantallas
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        // Pantalla de Inicio
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        // Pantalla Plaza de Armas
        composable(Screen.Plaza.route) {
            PlazaScreen(navController)
        }
        // Pantalla Mi Ubicación
        composable(Screen.MiUbicacion.route) {
            MiUbicacionScreen(navController)
        }
    }
}
