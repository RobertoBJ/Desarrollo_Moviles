package com.example.screensrobe

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.VolunteerActivism
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
import com.google.firebase.firestore.FirebaseFirestore
import com.example.screensrobe.R
import androidx.compose.ui.platform.LocalContext

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

    // --------- Cargar datos ---------
    LaunchedEffect(userId) {
        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { doc ->
                if (doc.exists()) userData = doc.data
                loading = false
            }
            .addOnFailureListener { loading = false }
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

        // -------- LOADING --------
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

        // -------- SIN DATOS --------
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

        // -------- UI PRINCIPAL --------
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // 🖼 FOTO
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

            // NOMBRE
            val displayName =
                if (data["isCompany"] as? Boolean == true) {
                    data["nombreEmpresa"] as? String ?: "Sin nombre"
                } else {
                    val nombre = data["nombre"] as? String ?: ""
                    val apellido = data["apellido"] as? String ?: ""
                    "$nombre $apellido".ifBlank { "Sin nombre" }
                }

            Text(displayName, style = MaterialTheme.typography.headlineMedium, fontSize = 24.sp)

            // TIPO
            val tipoText = if (data["isCompany"] as? Boolean == true) "Empresa" else "Usuario"
            Text(tipoText, style = MaterialTheme.typography.titleMedium, color = Color(0xFF3E9E8F))

            // -------- CARD INFORMACIÓN --------
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF3D3AC)),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {

                    val context = LocalContext.current
                    val direccion = "Av. Adolfo López Mateos #1801 Ote. Fracc. Bona Gens, C.P. 20255 Aguascalientes, Ags"

                    // EMPRESA
                    if (data["isCompany"] as? Boolean == true) {

                        Text("Email: ${data["email"] ?: "-"}", fontSize = 16.sp)
                        Text("Fecha de fundación: ${data["fechaFundacion"] ?: "-"}", fontSize = 16.sp)
                        Text("Tipo de donaciones: ${data["tipoDonaciones"] ?: "-"}", fontSize = 16.sp)

                        // ---------- BOTÓN MENSAJES ----------
                        Button(
                            onClick = {
                                navController.navigate("mensajes/$userId")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF59C1B8),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text("Mandar mensaje")
                        }


                        // ---------- MAPS ----------
                        Button(
                            onClick = {
                                val uri = Uri.parse("geo:0,0?q=${Uri.encode(direccion)}")
                                val i = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(i)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF3E9E8F),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_maps),
                                contentDescription = "Ubicación",
                                tint = Color.White
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Ver ubicación")
                        }

                        // ---------- DONACIONES MONETARIAS ----------
                        Button(
                            onClick = {
                                navController.navigate(Routes.METHOD) // Va a PaymentmethodScreen
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD48A6E),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.VolunteerActivism, contentDescription = null, tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text("Donaciones monetarias")
                        }

                        // ---------- DONACIONES FÍSICAS ----------
                        Button(
                            onClick = {
                                navController.navigate("donaciones_fisicas/$userId")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB27B55),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Inventory, contentDescription = null, tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text("Donaciones físicas")
                        }

                    } else {

                        // 👤 USUARIO
                        Text("Email: ${data["email"] ?: "-"}", fontSize = 16.sp)
                        Text("Fecha de nacimiento: ${data["fechaNacimiento"] ?: "-"}", fontSize = 16.sp)
                        Text("Género: ${data["genero"] ?: "-"}", fontSize = 16.sp)

                        // ---------- BOTÓN MENSAJES ----------
                        Button(
                            onClick = {
                                navController.navigate("mensajes/$userId")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF59C1B8),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                            Spacer(Modifier.width(8.dp))
                            Text("Mandar mensaje")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}
