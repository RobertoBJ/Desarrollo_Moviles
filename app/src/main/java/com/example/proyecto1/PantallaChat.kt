package com.example.proyecto1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto1.R

@Composable
fun PantallaChat(nombre: String, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        // Contenedor principal con scroll
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 80.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Ejemplo de mensajes
            ChatMessageBubble(
                text = "Buenos días, vi que publicaste un evento de donación?",
                fecha = "10:30 AM",
                imageRes = R.drawable.perfil1,
                isSender = false
            )
            ChatMessageBubble(
                text = "Yo también participaré",
                fecha = "10:32 AM",
                imageRes = R.drawable.perfil4,
                isSender = true
            )
            ChatMessageBubble(
                text = "Perfecto, nos vemos allí",
                fecha = "10:35 AM",
                imageRes = R.drawable.perfil1,
                isSender = false
            )
        }

        // Icono "volver" esquina superior izquierda
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 40.dp, start = 1.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver"
            )
        }

        // Texto centrado "Chat con ..."
        Text(
            text = stringResource(id = R.string.chat_with, nombre),
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
        )
    }
}

@Composable
fun ChatMessageBubble(
    text: String,
    fecha: String,
    imageRes: Int,
    isSender: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = if (isSender) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!isSender) {
            // Imagen + fecha a la izquierda
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Hora",
                        tint = Color.Gray,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = fecha, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        // Burbuja de mensaje
        Box(
            modifier = Modifier
                .background(
                    color = if (isSender) Color(0xFF4E8A82) else Color(0xFFF19D45),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(10.dp)
        ) {
            Text(text = text, color = if (isSender) Color.White else Color.Black)
        }

        if (isSender) {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}
