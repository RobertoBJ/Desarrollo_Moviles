package com.example.screensrobe


import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip

@Composable
fun SplashScreen(navController: NavController) {

    // Animación de fade-in
    val alpha = remember { Animatable(0f) }

    // Animación de escala (crecimiento)
    val scale = remember { Animatable(0.8f) }

    LaunchedEffect(true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            )
        )

        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = OvershootInterpolatorEasing(1.3f)
            )
        )

        delay(2200)

        // Cambia a Loading
        navController.navigate(Routes.LOADING) {
            popUpTo("splash") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDF6EE)),
        contentAlignment = Alignment.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.share_and_care),
            contentDescription = "Logo",
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .alpha(alpha.value)
                .scale(scale.value)
        )
    }
}

class OvershootInterpolatorEasing(private val tension: Float) : Easing {
    override fun transform(value: Float): Float {
        val t = value - 1.0f
        return t * t * ((tension + 1) * t + tension) + 1.0f
    }
}
