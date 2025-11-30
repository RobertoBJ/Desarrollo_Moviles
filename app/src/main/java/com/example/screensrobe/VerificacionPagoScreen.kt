package com.example.screensrobe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificacionPagoScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verificación de pago", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE5D8D8)
                )
            )
        },
        containerColor = Color.White
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Cuadro azul con palomita blanca
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(
                        Color(0xFF4A90E2),
                        androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✔",
                    fontSize = 90.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Pago realizado correctamente",
                fontSize = 22.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { navController.navigate("main") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A90E2)),
                modifier = Modifier.fillMaxWidth(),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
            ) {
                Text("Aceptar", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}
