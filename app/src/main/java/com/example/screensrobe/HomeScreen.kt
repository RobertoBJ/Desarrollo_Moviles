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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.screensrobe.MenuDesplegable
import com.example.screensrobe.R


data class HomePost(
    val userName: String,
    val content: String,
    val profilePicUrl: String? = null
)
@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()

    // Datos del usuario actual
    var userId by remember { mutableStateOf("") }
    var isCompany by remember { mutableStateOf(false) }
    var profilePicUrl by remember { mutableStateOf<String?>(null) }

    // Lista de posts
    val posts = remember { mutableStateListOf<HomePost>() }
    var isLoading by remember { mutableStateOf(true) }

    // Obtener datos del usuario actual
    LaunchedEffect(currentUser) {
        val uid = currentUser?.uid
        if (!uid.isNullOrEmpty()) {
            userId = uid
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        isCompany = doc.getBoolean("isCompany") ?: false
                        profilePicUrl = doc.getString("profileImage")
                    }
                }
        }
    }

    // Cargar posts
    LaunchedEffect(Unit) {
        firestore.collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    isLoading = false
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    posts.clear()
                    snapshot.documents.forEach { doc ->
                        val uid = doc.getString("userId")
                        if (!uid.isNullOrEmpty()) {
                            val userName = doc.getString("userName") ?: "Usuario"
                            val content = doc.getString("content") ?: ""

                            firestore.collection("users").document(uid).get()
                                .addOnSuccessListener { userDoc ->
                                    val profileImage = userDoc.getString("profileImage")
                                    posts.add(
                                        HomePost(
                                            userName = userName,
                                            content = content,
                                            profilePicUrl = profileImage
                                        )
                                    )
                                }
                        }
                    }
                }
                isLoading = false
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
                    IconButton(onClick = {
                        if (userId.isNotEmpty())
                            navController.navigate("profile/$userId/$isCompany")
                    }) {
                        if (!profilePicUrl.isNullOrEmpty()) {
                            AsyncImage(
                                model = profilePicUrl,
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
                    }

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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("public") },
                containerColor = Color(0xFF7EB5AB),
                shape = CircleShape,
                modifier = Modifier
                    .padding(bottom = 80.dp, end = 8.dp)
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
                .background(Color(0xFFF4F2EB))
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when {
                    isLoading -> {
                        repeat(3) { ShimmerPostCard() }
                    }
                    posts.isEmpty() -> {
                        Spacer(modifier = Modifier.height(40.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Aún no hay publicaciones",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                    else -> {
                        posts.forEach { post ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3D3AC)),
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

                                    if (post.content.isNotEmpty()) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = post.content,
                                            fontSize = 14.sp,
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
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
