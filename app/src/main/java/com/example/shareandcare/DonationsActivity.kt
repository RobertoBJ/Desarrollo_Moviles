package com.example.shareandcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast

class DonationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donations)

        // Configurar clics para cada tipo de donación
        setupDonationClicks()
    }

    private fun setupDonationClicks() {
        // Aquí puedes agregar la funcionalidad cuando se hace clic en cada tipo de donación
        // Por ahora solo mostramos un mensaje
        val donationItems = listOf("Ropa", "Libros", "Calzado", "Comida", "Otros")

        donationItems.forEach { item ->
            // En una app real, aquí asignarías los clics a cada elemento
        }

        Toast.makeText(this, "Selecciona un tipo de donación", Toast.LENGTH_SHORT).show()
    }
}