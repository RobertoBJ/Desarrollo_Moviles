package com.example.verificacionpagoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonVerificar = findViewById<Button>(R.id.btnVerificarPago)
        botonVerificar.setOnClickListener {
            val intent = Intent(this, VerificacionPagoActivity::class.java) // <- CORREGIDO
            startActivity(intent)
        }
    }
}
