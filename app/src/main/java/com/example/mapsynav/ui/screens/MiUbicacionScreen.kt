package com.example.mapsynav.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MiUbicacionScreen(navController: NavController) {
    // Estado del permiso de ubicación
    val permissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)

    Scaffold(//
        topBar = {
            TopAppBar(
                title = { Text("Mi Ubicación") },
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
        Box(modifier = Modifier.padding(paddingValues)) {
            // Verificamos si el permiso ha sido concedido
            if (permissionState.status.isGranted) {
                // Si el permiso está concedido, mostramos el mapa con la ubicación
                MapaUbicacionActual()
            } else {
                // Si no, mostramos un mensaje y el botón para solicitar el permiso
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Se necesita el permiso de GPS para mostrar tu ubicación.",
                        modifier = Modifier.padding(16.dp)
                    )
                    Button(onClick = { permissionState.launchPermissionRequest() }) {
                        Text("Conceder permiso")
                    }
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun MapaUbicacionActual() {
    val context = LocalContext.current
    // Cliente para obtener la ubicación
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    // Estado para guardar la ubicación actual
    var location by remember { mutableStateOf<LatLng?>(null) }
    
    // Coordenadas generales de Cajamarca, Perú
    val cajamarcaCentro = LatLng(-7.16378, -78.50027)

    // Estado de la cámara iniciando en Cajamarca
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cajamarcaCentro, 12f)
    }

    // Efecto para manejar las actualizaciones de ubicación
    DisposableEffect(Unit) {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 3000L).build()
        
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let {
                    val newLatLng = LatLng(it.latitude, it.longitude)
                    location = newLatLng
                    // Centramos la cámara en la ubicación real una vez obtenida
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(newLatLng, 15f)
                }
            }
        }

        // Empezamos a escuchar la ubicación
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        // Al destruir el composable, dejamos de escuchar
        onDispose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    // Si tenemos ubicación, mostramos el mapa
    location?.let { currentPos ->
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = currentPos),
                title = "Estás aquí"
            )
        }
    } ?: run {
        // Mientras carga la ubicación, mostramos un indicador de carga
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}
