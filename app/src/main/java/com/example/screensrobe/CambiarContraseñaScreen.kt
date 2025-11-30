package com.example.screensrobe

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CambiarContraseñaScreen(navController: NavController) {

    val context = LocalContext.current
    val db = Firebase.firestore

    var nueva by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }

    // 🔥 Limitar texto a 6 caracteres
    fun limitar(texto: String): String {
        return if (texto.length <= 6) texto else texto.take(6)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cambiar contraseña", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // NUEVA CONTRASEÑA
            TextField(
                value = nueva,
                onValueChange = { nueva = limitar(it) },
                label = { Text("Nueva contraseña (6 caracteres)") },
                modifier = Modifier.fillMaxWidth()
            )

            // CONFIRMAR CONTRASEÑA
            TextField(
                value = confirmar,
                onValueChange = { confirmar = limitar(it) },
                label = { Text("Confirmar contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {

                    when {
                        nueva.isBlank() || confirmar.isBlank() -> {
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        nueva.length != 6 -> {
                            Toast.makeText(context, "Debe tener 6 caracteres exactos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        nueva != confirmar -> {
                            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                    }

                    // 🔥 GUARDAR EN FIRESTORE
                    val datos = hashMapOf(
                        "nueva" to nueva,
                        "fecha" to System.currentTimeMillis()
                    )

                    db.collection("cambios_contraseña")
                        .add(datos)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Contraseña guardada correctamente", Toast.LENGTH_LONG).show()
                            navController.popBackStack()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_LONG).show()
                        }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Confirmar", fontSize = 18.sp)
            }
        }
    }
}
