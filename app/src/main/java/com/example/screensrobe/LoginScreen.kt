package com.example.screensrobe

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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

        // Caja central
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

                // Campo Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF9800),
                        unfocusedBorderColor = Color(0xFFFFC107)
                    )
                )

                // Campo Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = null
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF9800),
                        unfocusedBorderColor = Color(0xFFFFC107)
                    )
                )

                // Botón Login
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        auth.signInWithEmailAndPassword(email, password)
                            .addOnSuccessListener { result ->
                                val userId = result.user?.uid
                                if (userId != null) {
                                    db.collection("users").document(userId).get()
                                        .addOnSuccessListener { doc ->
                                            if (doc.exists()) {

                                                val isCompany = doc.getBoolean("isCompany") ?: false

                                                if (isCompany) {
                                                    // Empresa intentando entrar aquí
                                                    Toast.makeText(
                                                        context,
                                                        "Esta cuenta no esta registrada como usuario",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    auth.signOut()
                                                    return@addOnSuccessListener
                                                }

                                                //Usuario normal → permitir acceso
                                                navController.navigate(Routes.MAIN) {
                                                    popUpTo("login") { inclusive = true }
                                                }

                                            } else {
                                                Toast.makeText(context, "Cuenta no registrada", Toast.LENGTH_SHORT).show()
                                                auth.signOut()
                                            }
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                } else {
                                    Toast.makeText(context, "Error de autenticación", Toast.LENGTH_SHORT).show()
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
                    Text("Iniciar sesión", fontSize = 17.sp, fontWeight = FontWeight.Bold)
                }


                // Olvidó contraseña
                //TextButton(onClick = { /* TODO: recuperar contraseña */ }) {
                //    Text("¿Olvidaste la contraseña?", fontSize = 13.sp, color = Color.Gray)
                // }

                // Crear cuenta usuario
                Row(horizontalArrangement = Arrangement.Center) {
                    Text("¿No tienes cuenta? ", fontSize = 13.sp, color = Color.Gray)
                    ClickableText(
                        text = AnnotatedString("Regístrate"),
                        onClick = { navController.navigate(Routes.CREATE_ACCOUNT) },
                        style = LocalTextStyle.current.copy(
                            color = Color(0xFFFF9800),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                // Crear cuenta empresa
                ClickableText(
                    text = AnnotatedString("Registrarse como empresa"),
                    onClick = { navController.navigate(Routes.CREATE_COMPANY) },
                    style = LocalTextStyle.current.copy(
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                )

                // Login empresa
                ClickableText(
                    text = AnnotatedString("Iniciar sesión como empresa"),
                    onClick = { navController.navigate(Routes.LOGIN_ENTERPRISE) },
                    style = LocalTextStyle.current.copy(
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
