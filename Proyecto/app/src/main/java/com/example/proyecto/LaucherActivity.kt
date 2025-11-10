package com.example.proyecto

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import android.widget.Toast

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laucher)

        // Botón Configuración
        val btnConfig = findViewById<AppCompatImageButton>(R.id.btnConfig)
        btnConfig.setOnClickListener {
            Toast.makeText(this, "Configuración presionada", Toast.LENGTH_SHORT).show()
        }

        // Botón Tu Cuenta
        val btnCuenta = findViewById<Button>(R.id.btnCuenta)
        btnCuenta.setOnClickListener {
            Toast.makeText(this, "Tu cuenta presionada", Toast.LENGTH_SHORT).show()
        }

        // Botón Seguridad
        val btnSeguridad = findViewById<Button>(R.id.btnSeguridad)
        btnSeguridad.setOnClickListener {
            Toast.makeText(this, "Seguridad presionada", Toast.LENGTH_SHORT).show()
        }

        // Botón Menú
        val btnMenu = findViewById<AppCompatImageButton>(R.id.btnMenu)
        btnMenu.setOnClickListener {
            Toast.makeText(this, "Menú presionado", Toast.LENGTH_SHORT).show()
        }

        // Botón Califícanos
        val btnCalificanos = findViewById<Button>(R.id.btnCalificanos)
        btnCalificanos.setOnClickListener {
            Toast.makeText(this, "Califícanos presionado", Toast.LENGTH_SHORT).show()
        }
    }
}
