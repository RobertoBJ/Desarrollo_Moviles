package com.example.screensrobe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun OpinionesScreen(navController: NavHostController) {

    val db = FirebaseFirestore.getInstance()

    var rating by remember { mutableStateOf(0) }
    var guardado by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(
                color = Color(0xFFE5D8D8),
                shadowElevation = 2.dp,
                modifier = Modifier.statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color(0xFF5A9089),
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Califícanos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    MenuDesplegable(navController)   // 👈 YA FUNCIONA
                }
            }
        },
        containerColor = Color(0xFFF4F2EB)
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "¡Qué gusto saber de ti! ¿Qué tal te la estás pasando?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(180.dp)
                    .background(Color(0xFFFFF3D3), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dante),
                    contentDescription = "Imagen",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Por favor, comparte con nosotros qué te parece la aplicación",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Estrellas
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    Icon(
                        painter = painterResource(
                            id = if (i <= rating) R.drawable.ic_star_24 else R.drawable.ic_star_outline
                        ),
                        contentDescription = "Estrella $i",
                        modifier = Modifier
                            .size(42.dp)
                            .clickable { rating = i },
                        tint = Color.Unspecified
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    val data = hashMapOf(
                        "estrellas" to rating,
                        "fecha" to System.currentTimeMillis()
                    )

                    db.collection("opiniones")
                        .add(data)
                        .addOnSuccessListener { guardado = true }
                        .addOnFailureListener { guardado = false }
                },
                enabled = rating > 0,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3E9E8F)
                )
            ) {
                Text("Enviar calificación")
            }

            if (guardado) {
                Spacer(modifier = Modifier.height(20.dp))
                Text("¡Gracias por tu opinión! ❤️", color = Color(0xFF4CAF50))
            }
        }
    }
}
