package com.example.screensrobe

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@Composable
fun MainScreen() {
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
            0 -> PostScreen(modifier = Modifier.padding(innerPadding)) // Inicio
            1 -> PostScreen(modifier = Modifier.padding(innerPadding))
            2 -> PostScreen(modifier = Modifier.padding(innerPadding))
            3 -> PostScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}


