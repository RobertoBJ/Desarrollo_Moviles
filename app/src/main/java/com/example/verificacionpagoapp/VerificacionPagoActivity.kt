package com.example.verificacionpagoapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.verificacionpagoapp.EmpresaPerfilActivity  // Importa la actividad destino

class VerificacionPagoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verificacion_pago)

        val btnBack = findViewById<ImageView>(R.id.btnBack)
        btnBack.setOnClickListener {
            // Ir a EmpresaPerfilActivity
            val intent = Intent(this, EmpresaPerfilActivity::class.java)
            startActivity(intent)
            // Si no quieres que esta actividad quede en el stack:
            finish()
        }
    }
}
