package com.example.screensrobe.Pantalla6


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.screensrobe.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    // Delay de 2 segundos y navega a LoadingCardsScreen
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("loading") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Contenedor principal de splash
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.share_and_care),
            contentDescription = "Logo",
            modifier = Modifier.size(445.dp)
        )
    }
}
