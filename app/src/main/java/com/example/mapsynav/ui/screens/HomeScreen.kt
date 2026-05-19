package com.example.mapsynav.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mapsynav.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Explorar Cajamarca") },
            )
        }
    ) { paddingValues ->
        // Columna centrada para los botones
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón para ir a la Plaza de Armas
            Button(
                onClick = { navController.navigate(Screen.Plaza.route) },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Plaza de Armas")
            }

            // Espacio entre botones
            Spacer(modifier = Modifier.height(16.dp))

            // Botón para ir a Mi Ubicación
            Button(
                onClick = { navController.navigate(Screen.MiUbicacion.route) },
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Text("Mi Ubicación")
            }
        }
    }
}
