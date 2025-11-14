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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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


@Composable
fun AyudaScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            Surface(
                color = Color(0xFFF4F2EB),
                shadowElevation = 2.dp,
                modifier = Modifier.statusBarsPadding()

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás",
                            tint = Color(0xFF5A9089),
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    MenuDesplegable(navController)
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
                painter = painterResource(id = R.drawable.gojo),
                contentDescription = "Soporte",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            )

            Text(
                text = "Escribe tu problema:",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF3D3AC), RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text("Las personas podrán escribir su problema")
            }

            Text(
                text = "También puedes contactarnos por WhatsApp en caso de que tardemos mucho",
                fontSize = 14.sp,
                color = Color.Black
            )

            IconButton(
                onClick = {
                    val phone = "524493897227"  // ← pon el número con lada (México = 52)
                    val message = "Hola, necesito ayuda"
                    val url = "https://wa.me/$phone?text=${Uri.encode(message)}"

                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
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
