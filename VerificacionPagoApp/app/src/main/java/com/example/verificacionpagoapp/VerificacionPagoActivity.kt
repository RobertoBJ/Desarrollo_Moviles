package com.example.verificacionpagoapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView

class VerificacionPagoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificacion_pago)

        findViewById<ImageView>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}

