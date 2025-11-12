package com.example.proyecto1
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource

@Composable
fun PantallaMensajes(navController: NavController) {
    var mostrarMenuDerecho by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF8F8F8))
                    .padding(padding)
            ) {
                TopBarMensajes(onMenuClick = { mostrarMenuDerecho = !mostrarMenuDerecho })
                ChatMessages(navController)
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("opinion") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Icon(Icons.Default.Star, contentDescription = stringResource(R.string.rate_us))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.rate_us))
                }
            }

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
                            Icon(Icons.Default.Menu, contentDescription = null, tint = Color(0xFF69B1A3))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(R.string.menu), fontWeight = FontWeight.Bold)
                        }

                        Divider(color = Color(0xFF69B1A3))
                        Spacer(modifier = Modifier.height(12.dp))

                        MenuItem(icon = Icons.Default.Person, text = stringResource(R.string.profile)) { }
                        MenuItem(icon = Icons.Default.Notifications, text = stringResource(R.string.notifications)) { }
                        MenuItem(icon = Icons.Default.Help, text = stringResource(R.string.help)) {
                            navController.navigate("pantalla_ayuda")
                            mostrarMenuDerecho = false
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MenuItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text, tint = Color(0xFF69B1A3))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, color = Color.Black)
    }
}

@Composable
fun ChatMessages(navController: NavController) {
    Column(modifier = Modifier.padding(8.dp)) {
        ChatBubble("Buenos días, vi que publicaste un evento de donación?", R.drawable.perfil1, "Eduard", navController)
        ChatBubble("Yo donaré libros", R.drawable.perfil2, "Ashley Graham", navController)
        ChatBubble("Hola, leí tu comentario en la comunidad", R.drawable.perfil3, "Lady", navController)
        ChatBubble("Hola, vi tu publicación, yo también participaré", R.drawable.perfil4, "Leon S Kennedy", navController)
    }
}

@Composable
fun ChatBubble(text: String, imageRes: Int, nombre: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { navController.navigate("pantallaChat/$nombre") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .background(Color(0xFFF19D45), RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            Text(text)
        }
    }
}

@Composable
fun TopBarMensajes(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 0.dp, end = 16.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(color = Color(0xFFE0E0E0), shape = RoundedCornerShape(30))
                .padding(vertical = 10.dp, horizontal = 20.dp)
        ) {
            Text(
                text = stringResource(R.string.direct_message),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        IconButton(onClick = onMenuClick) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = stringResource(R.string.menu),
                tint = Color(0xFF69B1A3),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color(0xFFE0E0E0))
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { }) {
            Icon(Icons.Default.Home, contentDescription = "Inicio", tint = Color(0xFF69B1A3))
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color(0xFF69B1A3))
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Language, contentDescription = "Idioma", tint = Color(0xFF69B1A3))
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Mail, contentDescription = "Mensajes", tint = Color(0xFFB17469))
        }
    }
}