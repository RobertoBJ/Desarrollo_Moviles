package com.example.screensrobe

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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

@Composable
fun MenuDesplegable(navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    // Contenedor que sirve como ancla visual (sin mover el layout)
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

        // Menú desplegable
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(Color(0xFFF4F2EB))
                .width(180.dp),
            offset = DpOffset((-130).dp, 0.dp) // 👈 desplaza el menú hacia la izquierda
        ) {
            DropdownMenuItem(
                text = { Text("Configuracion") },
                onClick = {
                    expanded = false
                    navController.navigate("conf")
                }
            )
            DropdownMenuItem(
                text = { Text("Ayuda") },
                onClick = {
                    expanded = false
                    navController.navigate("Help")
                }
            )
        }
    }
}
