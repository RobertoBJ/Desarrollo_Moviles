package com.example.screensrobe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.screensrobe.R

data class Post(
    val userId: String = "",
    val userName: String = "",
    val content: String = "",
    val profileImage: String? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val currentUser = FirebaseAuth.getInstance().currentUser

    val posts = remember { mutableStateListOf<Post>() }
    var postsLoading by remember { mutableStateOf(true) }

    var thinkingText by remember { mutableStateOf(TextFieldValue("")) }
    var isWriting by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // 🔥 Cargar todas las publicaciones en tiempo real
    LaunchedEffect(true) {
        postsLoading = true
        db.collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                posts.clear()
                if (snapshot != null && snapshot.documents.isNotEmpty()) {
                    for (doc in snapshot.documents) {
                        posts.add(
                            Post(
                                userId = doc.getString("userId") ?: "",
                                userName = doc.getString("userName") ?: "Usuario",
                                content = doc.getString("content") ?: "",
                                profileImage = doc.getString("profileImage")
                            )
                        )
                    }
                }
                postsLoading = false
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Publicaciones", fontSize = 22.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.gojo),
                            contentDescription = "Volver",
                            modifier = Modifier.size(40.dp).clip(CircleShape),
                            tint = Color.Unspecified
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Color(0xFFE5D8D8))
            )
        },
        containerColor = Color(0xFFF4F2EB)
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ───────────────────────────────────────────────
            // 📌 Campo para crear publicación
            // ───────────────────────────────────────────────
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8D6A0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    val profilePic = currentUser?.photoUrl?.toString()
                    if (!profilePic.isNullOrEmpty()) {
                        AsyncImage(
                            model = profilePic,
                            contentDescription = "Perfil",
                            modifier = Modifier.size(45.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.gojo),
                            contentDescription = "Perfil",
                            modifier = Modifier.size(45.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    if (!isWriting) {
                        Text(
                            "¿Qué está pensando?",
                            fontSize = 17.sp,
                            modifier = Modifier.clickable {
                                isWriting = true
                                keyboardController?.show()
                            }
                        )
                    } else {
                        TextField(
                            value = thinkingText,
                            onValueChange = { thinkingText = it },
                            placeholder = { Text("Escribe algo...") },
                            modifier = Modifier.weight(1f),
                            maxLines = 3,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }

            // Botón publicar
            Button(
                onClick = {
                    if (thinkingText.text.isNotBlank() && currentUser != null) {
                        val post = hashMapOf(
                            "userId" to currentUser.uid,
                            "userName" to (currentUser.displayName ?: "Usuario"),
                            "profileImage" to (currentUser.photoUrl?.toString() ?: ""),
                            "content" to thinkingText.text,
                            "timestamp" to System.currentTimeMillis()
                        )

                        db.collection("posts").add(post)

                        thinkingText = TextFieldValue("")
                        isWriting = false
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E9E8F))
            ) {
                Text("Publicar", color = Color.White)
            }

            // ───────────────────────────────────────────────
            // 📌 Histórico de publicaciones (nuevo)
            // ───────────────────────────────────────────────
            Text(
                "Historial de publicaciones",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            when {
                postsLoading -> {
                    repeat(3) { ShimmerPostCard2() }
                }

                posts.isEmpty() -> {
                    Text("No hay publicaciones", color = Color.Gray)
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        items(posts) { post ->

                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF8D6A0)),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {

                                    Row(verticalAlignment = Alignment.CenterVertically) {

                                        if (!post.profileImage.isNullOrEmpty()) {
                                            AsyncImage(
                                                model = post.profileImage,
                                                contentDescription = "Perfil",
                                                modifier = Modifier.size(40.dp).clip(CircleShape),
                                                contentScale = ContentScale.Crop
                                            )
                                        } else {
                                            Image(
                                                painter = painterResource(id = R.drawable.gojo),
                                                contentDescription = "Perfil",
                                                modifier = Modifier.size(40.dp).clip(CircleShape)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            post.userName,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(post.content)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


// ───────────────────────────────────────────────
// ⭐ PLACEHOLDER SHIMMER
// ───────────────────────────────────────────────
@Composable
fun ShimmerPostCard2() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8E8E8)),
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {}
}
