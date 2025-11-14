package com.example.screensrobe

import TrendsScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun MainScreen(navController: NavController) {
    var selectedItem by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        }
    ) { innerPadding ->
        when (selectedItem) {
            0 -> HomeScreen(navController, modifier = Modifier.padding(innerPadding)) //
            1 -> TrendsScreen(navController, modifier = Modifier.padding(innerPadding))
            2 -> CommunityScreen(navController, modifier = Modifier.padding(innerPadding))
            3 -> MensajeDirectoScreen(navController, modifier = Modifier.padding(innerPadding))
        }
    }
}


