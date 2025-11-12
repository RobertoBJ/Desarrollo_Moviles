package com.example.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "mensajes"
            ) {
                // Pantalla principal con el menú derecho integrado
                composable("mensajes") { PantallaMensajes(navController) }

                // Chat individual
                composable("pantallaChat/{nombre}") { backStackEntry ->
                    val nombre = backStackEntry.arguments?.getString("nombre") ?: "Usuario"
                    PantallaChat(nombre, navController)
                }

                // Pantalla de opinión o calificación
                composable("opinion") { PantallaOpinion(navController) }

                // Pantalla de ayuda
                composable("pantalla_ayuda") { PantallaAyuda(navController) }

            }
        }
    }
}

