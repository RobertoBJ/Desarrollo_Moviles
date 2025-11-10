package com.example.seguridad

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ChangePasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)

        btnConfirm.setOnClickListener {
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                tvMessage.setTextColor(resources.getColor(android.R.color.holo_red_dark))
                tvMessage.text = "Las contraseñas no coinciden"
            } else {
                tvMessage.setTextColor(resources.getColor(android.R.color.holo_green_dark))
                tvMessage.text = "¡Contraseña confirmada por Koilin!"
            }
        }
    }
}

