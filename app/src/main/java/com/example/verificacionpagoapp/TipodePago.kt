package com.example.verificacionpagoapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TiposPagoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipos_pago)

        // Flecha de regreso
        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener { finish() }

        // PayPal
        val imgPaypal = findViewById<ImageView>(R.id.imgPaypal)
        imgPaypal.setOnClickListener {
            Toast.makeText(this, "Seleccionaste PayPal", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, VerificacionPagoActivity::class.java))
        }

        // Visa
        val imgVisa = findViewById<ImageView>(R.id.imgVisa)
        imgVisa.setOnClickListener {
            Toast.makeText(this, "Seleccionaste Visa", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, VerificacionPagoActivity::class.java))
        }

        // Citibanamex
        val imgCiti = findViewById<ImageView>(R.id.imgCiti)
        imgCiti.setOnClickListener {
            Toast.makeText(this, "Seleccionaste Citibanamex", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, VerificacionPagoActivity::class.java))
        }
    }
}
