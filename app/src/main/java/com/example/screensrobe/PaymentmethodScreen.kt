package com.example.screensrobe

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentmethodScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selecciona tu método de pago", fontSize = 20.sp) },
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
        containerColor = Color(0xFFF4F2EB)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // PAYPAL → TE MANDA A VERIFICACIÓN
            Image(
                painter = painterResource(id = R.drawable.paypal),
                contentDescription = "PayPal",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clickable { navController.navigate(Routes.PAYMENT) }
            )

            // MASTERCARD (centrado)
            Image(
                painter = painterResource(id = R.drawable.mastercard),
                contentDescription = "Mastercard",
                modifier = Modifier
                    .size(130.dp)
                    .clickable { }
            )

            // CITIBANAMEX
            Image(
                painter = painterResource(id = R.drawable.citibanamex),
                contentDescription = "Citibanamex",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .clickable { }
            )
        }
    }
}