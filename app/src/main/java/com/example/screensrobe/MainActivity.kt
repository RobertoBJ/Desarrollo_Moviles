package com.example.screensrobe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.activity.compose.setContent
import com.example.screensrobe.Pantalla6.LoadingCardsScreen
import com.example.screensrobe.Pantalla6.SplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.screensrobe.ui.theme.ScreensrobeTheme
import androidx.compose.runtime.LaunchedEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreensrobeTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "splash"
                ) {

                    // 🔹 Splash
                    composable("splash") { SplashScreen(navController) }

                    // 🔹 Pantalla de carga
                     composable("loading") { LoadingCardsScreen(navController) }

                    composable("login") { LoginScreen(navController) }
                    composable("create_account") { CreateAccountScreen(navController) }
                    composable("createcompany") { CreateCompanyAccountScreen(navController) }
                    composable("loginenterprise") { LoginEnterprise(navController) }
                    composable("profile") { ProfileScreen(navController) }
                    composable("public") { PostScreen(navController) }
                    composable("help") { AyudaScreen(navController) }
                    composable("conf") { ConfiguracionScreen(navController) }
                    composable("main") { MainScreen(navController) }
                    composable("opinion") { OpinionesScreen(navController) }
                }
            }
        }
    }
}
