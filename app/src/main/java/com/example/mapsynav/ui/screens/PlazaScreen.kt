package com.example.mapsynav.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlazaScreen(navController: NavController) {
    // Coordenadas actualizadas de la Plaza de Armas de Cajamarca según el enlace
    val plazaCajamarca = LatLng(-7.1569955, -78.5174615)
    
    // Estado de la cámara centrado en la plaza con zoom 17
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(plazaCajamarca, 17f)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plaza de Armas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        // Componente de Google Maps
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            cameraPositionState = cameraPositionState
        ) {
            // Marcador en la Plaza de Armas
            Marker(
                state = MarkerState(position = plazaCajamarca),
                title = "Plaza de Armas de Cajamarca",
                snippet = "Centro histórico de Cajamarca, Perú"
            )
        }
    }
}
