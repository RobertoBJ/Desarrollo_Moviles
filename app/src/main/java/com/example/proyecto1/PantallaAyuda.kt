package com.example.proyecto1

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaAyuda(navController: NavController) {
    var mostrarMenuDerecho by remember { mutableStateOf(false) }
    var texto by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F2))
                .padding(padding)
        ) {
            if (mostrarMenuDerecho) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .clickable { mostrarMenuDerecho = false }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    text = stringResource(id = R.string.help_title),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.perfil2),
                    contentDescription = "Ayuda imagen",
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.LightGray, CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text(stringResource(id = R.string.write_issue), fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = texto,
                    onValueChange = { texto = it },
                    placeholder = { Text(stringResource(id = R.string.write_issue_placeholder)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(Color(0xFFFFE4C4), RoundedCornerShape(8.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFFFE4C4),
                        unfocusedContainerColor = Color(0xFFFFE4C4),
                        disabledContainerColor = Color(0xFFFFE4C4),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    stringResource(id = R.string.contact_whatsapp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(16.dp))

                IconButton(
                    onClick = {
                        val mensaje = if (texto.isNotBlank()) {
                            "Hola, tuve un problema con la aplicación: $texto"
                        } else {
                            "Hola, tuve un problema con la aplicación."
                        }
                        val numero = "5214491440018"
                        val uri = Uri.parse("https://wa.me/$numero?text=${Uri.encode(mensaje)}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "WhatsApp",
                        modifier = Modifier.size(48.dp),
                        tint = Color(0xFF25D366)
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))
            }

            // Barra superior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(Color.Transparent)
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        tint = Color(0xFF69B1A3),
                        modifier = Modifier.size(36.dp)
                    )
                }

                IconButton(onClick = { mostrarMenuDerecho = !mostrarMenuDerecho }) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = stringResource(id = R.string.menu),
                        tint = Color(0xFF69B1A3),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            // Menú lateral
            if (mostrarMenuDerecho) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .clickable { mostrarMenuDerecho = false }
                )

                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .width(180.dp)
                        .align(Alignment.TopEnd)
                        .background(Color(0xFFD9D9D9), RoundedCornerShape(bottomStart = 16.dp))
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .padding(12.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 8.dp)
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = null, tint = Color(0xFF4E8A82))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(id = R.string.menu), fontWeight = FontWeight.Bold)
                        }

                        Divider(color = Color(0xFF4E8A82))
                        Spacer(modifier = Modifier.height(12.dp))

                        MenuItem(icon = Icons.Default.Person, text = stringResource(id = R.string.profile)) {}
                        MenuItem(icon = Icons.Default.Notifications, text = stringResource(id = R.string.notifications)) {}
                        MenuItem(icon = Icons.Default.Help, text = stringResource(id = R.string.help)) {
                            navController.navigate("pantalla_ayuda")
                            mostrarMenuDerecho = false
                        }
                    }
                }
            }
        }
    }
}
