package com.example.screensrobe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MenuDesplegable(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menú",
                tint = Color(0xFF5A9089)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color(0xFFF4F2EB))
                .width(180.dp),
            offset = DpOffset((-130).dp, 0.dp)
        ) {
            DropdownMenuItem(
                text = { Text("Configuración") },
                onClick = {
                    expanded = false
                    navController.navigate(Routes.CONFIG)
                }
            )

            DropdownMenuItem(
                text = { Text("Ayuda") },
                onClick = {
                    expanded = false
                    navController.navigate(Routes.HELP)
                }
            )

            Divider()

            // CERRAR SESIÓN
            DropdownMenuItem(
                text = { Text("Cerrar sesión", color = Color.Red) },
                onClick = {
                    expanded = false
                    FirebaseAuth.getInstance().signOut()

                    // Limpia todo el stack de navegación
                    navController.navigate("login") {
                        popUpTo(0) // elimina toda la navegación previa
                    }
                }
            )
        }
    }
}
