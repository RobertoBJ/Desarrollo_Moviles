package com.example.screensrobe

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmpresaScreen(navController: NavController) {

    val context = LocalContext.current   // 👉 Necesario para Instagram y Maps

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Empresa", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE5D8D8)
                )
            )
        },
        containerColor = Color.White
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // FOTO CIRCULAR
            Image(
                painter = painterResource(id = R.drawable.logo_empresa),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "Fundación Ayuda Solidaria",
                fontSize = 23.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFD9D9D9), RoundedCornerShape(12.dp))
                    .padding(15.dp)
            ) {
                Text(
                    text = "APOYAMOS A LA COMUNIDAD CON DONACIONES Y SOLIDARIDAD.",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Tipo de Donaciones",
                fontSize = 21.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(25.dp))

            // BOTONES DE DONACIÓN
            Row(
                horizontalArrangement = Arrangement.spacedBy(25.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3A978)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("LIBROS", color = Color.Black)
                }

                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3A978)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("DINERO", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ICONOS COMO BOTONES (INSTAGRAM Y CARRITO)
            Row(
                horizontalArrangement = Arrangement.spacedBy(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // 👉 INSTAGRAM
                Image(
                    painter = painterResource(id = R.drawable.icon_instagram),
                    contentDescription = "Instagram",
                    modifier = Modifier
                        .size(45.dp)
                        .clickable {
                            val url = "https://instagram.com/tu_usuario" // pon tu usuario real
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        }
                )

                // 👉 CARRITO → MAPS
                Image(
                    painter = painterResource(id = R.drawable.icon_car),
                    contentDescription = "Carrito",
                    modifier = Modifier
                        .size(45.dp)
                        .clickable {
                            val urlMaps = "https://www.google.com/maps"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlMaps))
                            context.startActivity(intent)
                        }
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            // EDITAR PERFIL
            Text(
                text = "Editar perfil",
                fontSize = 18.sp,
                color = Color(0xFF4A90E2),
                modifier = Modifier
                    .clickable { /* Acción editar perfil */ }
            )

            Spacer(modifier = Modifier.height(35.dp))

            // BOTÓN CAMBIO DE IDIOMA
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD3D3D3)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("CAMBIO DE IDIOMA", color = Color.Black, fontSize = 17.sp)
            }
        }
    }
}
