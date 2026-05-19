package com.example.mapsynav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mapsynav.ui.navigation.AppNavigation
import com.example.mapsynav.ui.theme.MapsynavTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Habilitar diseño de borde a borde (edge-to-edge)
        enableEdgeToEdge()
        setContent {
            // Usamos el tema definido para la aplicación
            MapsynavTheme {
                // Punto de entrada de la navegación
                AppNavigation()
            }
        }
    }
}
