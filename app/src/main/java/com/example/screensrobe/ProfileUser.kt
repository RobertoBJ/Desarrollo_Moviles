package com.example.screensrobe

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.screensrobe.R
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId: String,
    navController: NavController,
    isCompany: Boolean = false
) {
    val firestore = FirebaseFirestore.getInstance()
    var userData by remember { mutableStateOf<Map<String, Any>?>(null) }
    var loading by remember { mutableStateOf(true) }

    // Lista de posts reactiva
    val posts = remember { mutableStateListOf<Map<String, Any>>() }
    var postsLoading by remember { mutableStateOf(true) }

    // Cargar datos del usuario
    LaunchedEffect(userId) {
        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) userData = doc.data
                loading = false
            }
            .addOnFailureListener { loading = false }
    }

    // Listener en tiempo real para los posts del usuario
    DisposableEffect(userId) {
        val listener = firestore.collection("posts")
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                posts.clear()
                snapshot?.documents?.forEach { doc ->
                    posts.add(doc.data ?: emptyMap())
                }
                postsLoading = false
            }

        onDispose { listener.remove() }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color(0xFFE5D8D8))
            )
        },
        containerColor = Color(0xFFF4F2EB)
    ) { padding ->
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF59C1B8))
            }
            return@Scaffold
        }

        val data = userData
        if (data == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontraron datos", color = Color.Gray, fontSize = 18.sp)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Imagen de perfil
            val profileImage = data["profileImage"] as? String
            if (!profileImage.isNullOrEmpty()) {
                AsyncImage(
                    model = profileImage,
                    contentDescription = "Imagen de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.gojo),
                    contentDescription = "Perfil placeholder",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            // Nombre
            val displayName = if (data["isCompany"] as? Boolean == true) {
                data["nombreEmpresa"] as? String ?: "Sin nombre"
            } else {
                val nombre = data["nombre"] as? String ?: ""
                val apellido = data["apellido"] as? String ?: ""
                "$nombre $apellido".ifBlank { "Sin nombre" }
            }
            Text(displayName, style = MaterialTheme.typography.headlineMedium, fontSize = 24.sp)

            // Tipo
            val tipoText = if (data["isCompany"] as? Boolean == true) "Empresa" else "Usuario"
            Text(tipoText, style = MaterialTheme.typography.titleMedium, color = Color(0xFF3E9E8F))

            // Información en Card
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3D3AC)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (data["isCompany"] as? Boolean == true) {
                        Text("Email: ${data["email"] ?: "-"}", fontSize = 16.sp)
                        Text("Fecha de fundación: ${data["fechaFundacion"] ?: "-"}", fontSize = 16.sp)
                        Text("Tipo de donaciones: ${data["tipoDonaciones"] ?: "-"}", fontSize = 16.sp)
                    } else {
                        Text("Email: ${data["email"] ?: "-"}", fontSize = 16.sp)
                        Text("Fecha de nacimiento: ${data["fechaNacimiento"] ?: "-"}", fontSize = 16.sp)
                        Text("Género: ${data["genero"] ?: "-"}", fontSize = 16.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Historial de publicaciones
            Text("Historial de publicaciones", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(10.dp))

            when {
                postsLoading -> {
                    // Placeholders estilo Shimmer
                    repeat(3) { ShimmerPostCard() }
                }
                posts.isEmpty() -> {
                    Text("No hay publicaciones", color = Color.Gray)
                }
                else -> {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        posts.forEach { post ->
                            val content = post["content"] as? String ?: ""
                            val timestamp = post["timestamp"] as? Long
                            val timeText = timestamp?.let {
                                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                                sdf.format(Date(it))
                            } ?: ""
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3D3AC)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(content, fontSize = 16.sp)
                                    if (timeText.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(timeText, fontSize = 12.sp, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

/** Shimmer placeholder */
@Composable
fun ShimmerPostCard() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val brush = rememberShimmerBrush(shimmerColors)

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3D3AC)),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        )
    }
}

@Composable
fun rememberShimmerBrush(colors: List<Color>): Brush {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    return Brush.linearGradient(
        colors = colors,
        start = Offset(translateAnim, translateAnim),
        end = Offset(translateAnim + 200f, translateAnim + 200f)
    )
}
