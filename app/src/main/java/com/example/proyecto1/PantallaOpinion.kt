package com.example.proyecto1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaOpinion(navController: NavController) {
    var rating by remember { mutableStateOf(0) }
    var mostrarMenuDerecho by remember { mutableStateOf(false) }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.opinion_greeting),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    lineHeight = 28.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFF8E7)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dante),
                        contentDescription = "Personaje",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(160.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(stringResource(id = R.string.opinion_prompt), fontSize = 18.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(id = R.string.opinion_description), fontSize = 16.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(16.dp))

                Row(horizontalArrangement = Arrangement.Center) {
                    for (i in 1..5) {
                        val starColor = if (i <= rating) Color(0xFFFFD700) else Color.Gray
                        Icon(
                            imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Star $i",
                            tint = starColor,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { rating = i }
                                .padding(4.dp)
                        )
                    }
                }
            }

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

            if (mostrarMenuDerecho) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f))
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
                            Text(stringResource(id = R.string.menu), fontWeight = FontWeight.Bold)
                        }

                        Divider(color = Color(0xFF69B1A3))
                        Spacer(modifier = Modifier.height(12.dp))

                        MenuItem(icon = Icons.Default.Person, text = stringResource(id = R.string.profile)) { mostrarMenuDerecho = false }
                        MenuItem(icon = Icons.Default.Notifications, text = stringResource(id = R.string.notifications)) { mostrarMenuDerecho = false }
                        MenuItem(icon = Icons.Default.Help, text = stringResource(id = R.string.help)) {
                            mostrarMenuDerecho = false
                            navController.navigate("pantalla_ayuda")
                        }
                    }
                }
            }
        }
    }
}
