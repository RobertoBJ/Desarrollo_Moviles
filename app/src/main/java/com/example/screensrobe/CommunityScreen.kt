package com.example.screensrobe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

@Composable
fun CommunityScreen(navController: NavController, modifier: Modifier = Modifier) {

    // 🎯 LISTA DINÁMICA DE FIRESTORE
    val posts = remember { mutableStateListOf<CommunityPost>() }

    // 🔥 TRAER DATOS EN TIEMPO REAL
    LaunchedEffect(true) {
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    posts.clear()

                    for (doc in snapshot.documents) {
                        val userId = doc.getString("userId") ?: continue

                        // 🔍 Consultar si el usuario es empresa o no
                        firestore.collection("users")
                            .document(userId)
                            .get()
                            .addOnSuccessListener { userDoc ->
                                val isCompany = userDoc.getBoolean("isCompany") ?: false

                                // ❗ Solo agregar si NO es empresa
                                if (!isCompany) {
                                    val profileImage = userDoc.getString("profileImage")
                                    posts.add(
                                        CommunityPost(
                                            userName = doc.getString("userName") ?: "Usuario",
                                            content = doc.getString("content") ?: "",
                                            profilePicUrl = profileImage,
                                            profilePic = R.drawable.gojo
                                        )
                                    )
                                }
                            }
                    }
                }
            }
    }


    Scaffold(
        topBar = {
            Surface(
                color = Color(0xFFE5D8D8),
                shadowElevation = 4.dp,
                modifier = Modifier.statusBarsPadding()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Comunidad",
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

        //Botón de nueva publicación
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.PUBLIC) },
                containerColor = Color(0xFF7EB5AB),
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 80.dp, end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar",
                    tint = Color.White
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = Color(0xFFF4F2EB)
    ) { padding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFFDF6EE))
                .padding(padding)
                .padding(16.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // 🔥 AHORA ITERA LOS DATOS DE FIRESTORE
                posts.forEach { post ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8D6A0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (!post.profilePicUrl.isNullOrEmpty()) {
                                    AsyncImage(
                                        model = post.profilePicUrl,
                                        contentDescription = "Perfil",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.gojo),
                                        contentDescription = "Perfil",
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = post.userName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = post.content,
                                fontSize = 15.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// MODELO actualizado para contener varias imágenes
data class CommunityPost(
    val userName: String,
    val content: String,
    val profilePicUrl: String? = null,
    val profilePic: Int
)
