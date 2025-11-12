package com.example.verificacionpagoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView


class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)
        val btnBack = findViewById<ImageView>(R.id.btnBack)


        btnConfirm.setOnClickListener {
            if (etPassword.text.toString() == etConfirmPassword.text.toString()) {
                Toast.makeText(this, "Contraseña cambiada", Toast.LENGTH_SHORT).show()
                tvMessage.text = ""
            } else {
                tvMessage.text = "Las contraseñas no coinciden"
            }
        }

        btnBack.setOnClickListener { finish() }
    }
}
