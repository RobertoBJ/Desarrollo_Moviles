package com.example.screensrobe

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LoginEnterprise(navController: NavController) {

    val context = LocalContext.current
    var correo by remember { mutableStateOf("") }
    var contraseña by remember { mutableStateOf("") }
    var contraseñaVisible by remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    Box(modifier = Modifier.fillMaxSize()) {

        // Fondo
        Image(
            painter = painterResource(id = R.drawable.fondo_login),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        // Contenedor principal
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(22.dp)
                .background(Color.White.copy(alpha = 0.95f), RoundedCornerShape(22.dp))
                .padding(26.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                Text(
                    "Inicio de Sesión Empresarial",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color(0xFF333333)
                )

                // Correo empresarial
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo electrónico empresa") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = null)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF9800),
                        unfocusedBorderColor = Color(0xFFFFC107)
                    )
                )

                // Contraseña
                OutlinedTextField(
                    value = contraseña,
                    onValueChange = { contraseña = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = null)
                    },
                    trailingIcon = {
                        IconButton(onClick = { contraseñaVisible = !contraseñaVisible }) {
                            Icon(
                                imageVector = if (contraseñaVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = if (contraseñaVisible)
                        VisualTransformation.None
                    else PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF9800),
                        unfocusedBorderColor = Color(0xFFFFC107)
                    )
                )

                // Botón iniciar sesión
                Button(
                    onClick = {

                        if (correo.isBlank() || contraseña.isBlank()) {
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        auth.signInWithEmailAndPassword(correo, contraseña)
                            .addOnSuccessListener { result ->
                                val userId = result.user?.uid
                                if (userId != null) {
                                    db.collection("users").document(userId).get()
                                        .addOnSuccessListener { doc ->
                                            if (doc.exists()) {
                                                val isCompany = doc.getBoolean("isCompany") ?: false

                                                if (!isCompany) {
                                                    Toast.makeText(context, "Solo empresas pueden iniciar sesión aquí", Toast.LENGTH_SHORT).show()
                                                    auth.signOut()
                                                    return@addOnSuccessListener
                                                }

                                                // ✔️ Empresa válida → navegar al Main
                                                navController.navigate(Routes.MAIN) {
                                                    popUpTo("loginenterprise") { inclusive = true }
                                                }
                                            } else {
                                                Toast.makeText(context, "Cuenta no registrada", Toast.LENGTH_SHORT).show()
                                                auth.signOut()
                                            }
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error al iniciar sesión: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF59C1B8)),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(48.dp)
                ) {
                    Text("Iniciar Sesión", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                }

                // Crear empresa
                TextButton(onClick = { navController.navigate(Routes.CREATE_COMPANY) }) {
                    Text(
                        "¿Tu empresa no está registrada? Crear cuenta",
                        fontSize = 13.sp,
                        color = Color(0xFFFF9800)
                    )
                }
            }
        }
    }
}
