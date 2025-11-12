package com.example.screensrobe


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavBar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    val items = listOf(
        Icons.Default.Home,
        Icons.Default.Search,
        Icons.Default.Public,
        Icons.Default.Person
    )

    val labels = listOf("Inicio", "Buscar", "Explorar", "Perfil")

    NavigationBar(
        containerColor = Color(0xFFE5D8D8),
        tonalElevation = 8.dp,
        modifier = Modifier.shadow(
            elevation = 10.dp,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        )
    ) {
        items.forEachIndexed { index, icon ->
            val isSelected = selectedItem == index
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.25f else 1f,
                label = "scaleAnim"
            )

            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(index) },
                alwaysShowLabel = true, // ✅ fuerza a mostrar el texto siempre
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = labels[index],
                        modifier = Modifier
                            .size(26.dp)
                            .scale(scale),
                        tint = if (isSelected) Color.Black else Color(0xFF6AB7A8)
                    )
                },
                label = {
                    Text(
                        text = labels[index],
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) Color.Black else Color(0xFF6AB7A8)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color(0xFF6AB7A8),
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color(0xFF6AB7A8),
                    indicatorColor = Color(0xFF59C1B8) // Fondo resaltado
                )
            )
        }
    }
}


