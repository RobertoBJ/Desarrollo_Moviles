package com.example.screensrobe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.screensrobe.ui.theme.ScreensrobeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreensrobeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("create_account") { CreateAccountScreen() }
                    composable("createcompany") { CreateCompanyAccountScreen() }
                    composable("loginenterprise") { LoginEnterprise(navController) }
                    composable("profile") { ProfileScreen(navController) }
                    composable("public") { PostScreen(navController) }
                    composable("help") { AyudaScreen(navController) }
                    composable("conf") { ConfiguracionScreen(navController) }
                    composable("main") { MainScreen(navController) } // Pantalla con barra inferior

                }
            }
        }
    }
}