package com.example.proyecto1


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "mensajes"
    ) {
        composable("mensajes") {
            PantallaMensajes(navController)
        }
        composable("pantallaChat/{nombre}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: "Usuario"
            PantallaChat(nombre, navController)
        }
    }
}
