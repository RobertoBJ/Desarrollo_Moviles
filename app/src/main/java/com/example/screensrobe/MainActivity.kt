package com.example.screensrobe


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.screensrobe.ui.theme.ScreensrobeTheme

// Definir rutas fuera de MainActivity
object Routes {
    const val LOGIN = "login"
    const val CREATE_ACCOUNT = "create_account"
    const val CREATE_COMPANY = "create_company"
    const val LOGIN_ENTERPRISE = "login_enterprise"
    const val PROFILE = "profile/{userId}/{isCompany}"
    const val PUBLIC = "public"
    const val HELP = "help"
    const val CONFIG = "conf"
    const val MAIN = "main"
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreensrobeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.LOGIN) {
                    composable(Routes.LOGIN) { LoginScreen(navController) }
                    composable(Routes.CREATE_ACCOUNT) { CreateAccountScreen(navController) }
                    composable(Routes.CREATE_COMPANY) { CreateCompanyAccountScreen(navController) }
                    composable(Routes.LOGIN_ENTERPRISE) { LoginEnterprise(navController) }
                    composable(
                        "profile/{userId}/{isCompany}",
                        arguments = listOf(
                            navArgument("userId") { type = NavType.StringType },
                            navArgument("isCompany") { type = NavType.BoolType }
                        )
                    ) { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        val isCompany = backStackEntry.arguments?.getBoolean("isCompany") ?: false
                        ProfileScreen(userId = userId, navController = navController, isCompany = isCompany)
                    }
                    composable(Routes.PUBLIC) { PostScreen(navController) }
                    composable(Routes.HELP) { AyudaScreen(navController) }
                    composable(Routes.CONFIG) { ConfiguracionScreen(navController) }
                    composable(Routes.MAIN) { MainScreen(navController) }
                }
            }
        }
    }
}
