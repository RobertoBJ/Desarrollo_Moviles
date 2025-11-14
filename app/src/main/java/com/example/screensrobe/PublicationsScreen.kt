package com.example.screensrobe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class Post(val userName: String, val content: String, val profilePic: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(navController: NavController, modifier: Modifier = Modifier) {
    val posts = listOf(
        Post("Roberto", "Hoy iniciamos con la campaña de apoyo 💚", R.drawable.gojo),
        Post("Maria", "Agradezco a todos por su colaboración 🙌", R.drawable.gojo),
        Post("Carlos", "Donamos más de 100 alimentos esta semana 🍎", R.drawable.gojo)
    )

    // Estados del campo de texto
    var thinkingText by remember { mutableStateOf(TextFieldValue("")) }
    var isWriting by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            Surface(
                color = Color(0xFFE5D8D8),
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Foto de perfil o logo
                    IconButton(onClick = { navController.navigate("profile")}) {
                        Icon(
                            painter = painterResource(id = R.drawable.gojo),
                            contentDescription = "Perfil",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            tint = Color.Unspecified
                        )
                    }

                    // Título
                    Text(
                        text = "Publicaciones",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontSize = 22.sp
                        )
                    )

                    MenuDesplegable(navController)
                }
            }
        },
        containerColor = Color(0xFFFDFDFD)
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botón de publicar
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            if (thinkingText.text.isNotBlank()) {
                                thinkingText = TextFieldValue("")
                                isWriting = false
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E9E8F))
                    ) {
                        Text("Publicar", fontWeight = FontWeight.Bold)
                    }
                }

                // Card del pensamiento
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8D6A0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gojo),
                            contentDescription = "Foto de perfil",
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        if (!isWriting) {
                            // Texto clickable
                            Text(
                                text = "¿Qué está pensando?",
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 17.sp),
                                modifier = Modifier
                                    .clickable {
                                        isWriting = true
                                        focusRequester.requestFocus()
                                        keyboardController?.show()
                                    }
                            )
                        } else {
                            // Campo de texto editable
                            TextField(
                                value = thinkingText,
                                onValueChange = { thinkingText = it },
                                placeholder = { Text("Escribe algo...") },
                                modifier = Modifier
                                    .weight(1f)
                                    .focusRequester(focusRequester),
                                maxLines = 3,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                )
                            )
                        }
                    }
                }

                // Iconos inferiores
                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = { navController.navigate("main")}) {
                        Icon(
                            painter = painterResource(id = R.drawable.galeria),
                            contentDescription = "Archivo",
                            tint = Color.Unspecified
                        )
                    }
                    IconButton(onClick = { /* TODO agregar GIF */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.gif),
                            contentDescription = "GIF",
                            tint = Color.Unspecified
                        )
                    }
                }

                // Lista de publicaciones
                Text(
                    text = "Últimas publicaciones",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(posts) { post ->
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8D6A0)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(id = post.profilePic),
                                        contentDescription = "Perfil",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = post.userName,
                                        style = MaterialTheme.typography.bodyLarge.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(text = post.content)
                            }
                        }
                    }
                }
            }
        }
    }
}

