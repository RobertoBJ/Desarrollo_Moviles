package com.example.proyecto1
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*

class Logoinicio : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Fondo blanco con logo centrado
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logoshare), // reemplaza con tu logo
                    contentDescription = "Logo de inicio",
                    modifier = Modifier.size(500.dp)
                )
            }
        }

        // Espera 2 segundos y abre MainActivity
        CoroutineScope(Dispatchers.Main).launch {
            delay(2000) // 2 segundos
            startActivity(Intent(this@Logoinicio, MainActivity::class.java))
            finish()
        }
    }
}
