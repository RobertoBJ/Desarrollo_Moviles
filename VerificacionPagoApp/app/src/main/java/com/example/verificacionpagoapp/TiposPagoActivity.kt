package com.example.verificacionpagoapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class TiposPagoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tipos_pago)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }

        // Puedes hacer click en las imágenes para simular pago
        findViewById<ImageView>(R.id.imgPaypal).setOnClickListener { abrirVerificacion() }
        findViewById<ImageView>(R.id.imgVisa).setOnClickListener { abrirVerificacion() }
        findViewById<ImageView>(R.id.imgCiti).setOnClickListener { abrirVerificacion() }
    }

    private fun abrirVerificacion() {
        startActivity(Intent(this, VerificacionPagoActivity::class.java))
    }
}
