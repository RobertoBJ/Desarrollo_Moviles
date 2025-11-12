package com.example.verificacionpagoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laucher) // Asegúrate que el nombre coincide con tu XML

        val btnConfig = findViewById<AppCompatImageButton>(R.id.btnConfig)
        btnConfig.setOnClickListener {
            startActivity(Intent(this, VerificacionPagoActivity::class.java))
        }

        val btnCuenta = findViewById<Button>(R.id.btnCuenta)
        btnCuenta.setOnClickListener {
            startActivity(Intent(this, TiposPagoActivity::class.java))
        }

        val btnSeguridad = findViewById<Button>(R.id.btnSeguridad)
        btnSeguridad.setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }

        val btnCalificanos = findViewById<Button>(R.id.btnCalificanos)
        btnCalificanos.setOnClickListener {
            Toast.makeText(this, "Califícanos presionado", Toast.LENGTH_SHORT).show()
        }
    }
}
