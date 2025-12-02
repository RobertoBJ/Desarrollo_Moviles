package com.example.screensrobe

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// VisualTransformation para formato dd/MM/yyyy
class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 8) text.text.substring(0..7) else text.text
        val formatted = buildString {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (i == 1 || i == 3) append("/")
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 1 -> offset
                    offset <= 3 -> offset + 1
                    offset <= 8 -> offset + 2
                    else -> 10
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset - 1
                    offset <= 10 -> offset - 2
                    else -> 8
                }
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}

@Composable
fun CreateAccountScreen(navController: NavController) {

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var name by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    "Crear Cuenta",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                // Nombre
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Apellido
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Apellido") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Género
                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Género (Hombre/Mujer)") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Fecha de nacimiento con formato automático
                OutlinedTextField(
                    value = date,
                    onValueChange = {
                        if (it.length <= 8 && it.all { c -> c.isDigit() }) {
                            date = it
                        }
                    },
                    label = { Text("Fecha de nacimiento (dd/MM/yyyy)") },
                    visualTransformation = DateVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                // Correo
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
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
                    modifier = Modifier.fillMaxWidth()
                )

                // Botón Crear Cuenta
                Button(
                    onClick = {
                        if (name.isBlank() || firstName.isBlank() || gender.isBlank() || date.isBlank() || email.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener { result ->
                                val uid = result.user?.uid
                                if (uid != null) {
                                    val userData = hashMapOf(
                                        "nombre" to name,
                                        "apellido" to firstName,
                                        "genero" to gender,
                                        "fechaNacimiento" to date,
                                        "email" to email,
                                        "isCompany" to false // false = usuario
                                    )
                                    db.collection("users").document(uid).set(userData)
                                        .addOnSuccessListener {
                                            Toast.makeText(context, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()
                                            navController.navigate("login") {
                                                popUpTo("create_account") { inclusive = true }
                                            }
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(context, "Error al guardar datos: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                } else {
                                    Toast.makeText(context, "Error al crear la cuenta", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                            }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF59C1B8)),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(48.dp)
                ) {
                    Text("Crear Cuenta", fontWeight = FontWeight.Bold)
                }

            }
        }
    }
}
