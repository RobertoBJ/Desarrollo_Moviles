package com.example.screensrobe

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AyudaScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()

    var problema by remember { mutableStateOf("") }
    var enviado by remember { mutableStateOf(false) }

    val context = LocalContext.current

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
                        text = "Ayuda",
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
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "¿En qué podemos ayudarte?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                painter = painterResource(id = R.drawable.perfil2),
                contentDescription = "Soporte",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "Describe tu problema:",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            TextField(
                value = problema,
                onValueChange = {
                    problema = it
                    enviado = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color(0xFFF3D3AC), RoundedCornerShape(12.dp)),
                placeholder = { Text("Escribe tu problema aquí...") }
            )

            Button(
                onClick = {
                    val user = auth.currentUser

                    val reporte = hashMapOf(
                        "problema" to problema,
                        "userId" to (user?.uid ?: "sin_id"),
                        "correo" to (user?.email ?: "sin_correo"),
                        "timestamp" to System.currentTimeMillis()
                    )

                    db.collection("reportesAyuda")
                        .add(reporte)
                        .addOnSuccessListener {
                            problema = ""
                            enviado = true
                        }
                        .addOnFailureListener { enviado = false }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3E9E8F)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar reporte")
            }

            if (enviado) {
                Text(
                    text = "¡Gracias! Tu reporte ha sido enviado 💚",
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Text(
                text = "Si tardamos mucho, escríbenos por WhatsApp:",
                fontSize = 14.sp,
                color = Color.Black
            )

            IconButton(
                onClick = {
                    val url = "https://wa.me/524493897227?text=${Uri.encode("Hola, necesito ayuda")}"
                    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                },
                modifier = Modifier
                    .size(56.dp)
                    .background(Color(0xFF7EB5AB), CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.whatsapp),
                    contentDescription = "WhatsApp",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}