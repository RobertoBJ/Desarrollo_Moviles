package com.example.verificacionpagoapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EmpresaPerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_empresa_perfil)

        val imageCompany = findViewById<ImageView>(R.id.image_company)
        val textCompanyName = findViewById<TextView>(R.id.text_company_name)
        val buttonBack = findViewById<ImageButton>(R.id.button_back)
        val buttonInfo = findViewById<Button>(R.id.button_info)
        val buttonBooks = findViewById<Button>(R.id.button_books)
        val buttonMoney = findViewById<Button>(R.id.button_money)
        val buttonInstagram = findViewById<ImageButton>(R.id.button_instagram)
        val buttonCar = findViewById<ImageButton>(R.id.button_car)
        val textEditProfile = findViewById<TextView>(R.id.text_edit_profile)
        val buttonChangeLanguage = findViewById<Button>(R.id.button_change_language)

        // Configuración inicial
        textCompanyName.text = "Fundación Ayuda Solidaria"

        // Botón regresar
        buttonBack.setOnClickListener { finish() }

        // Botón información
        buttonInfo.setOnClickListener {
            Toast.makeText(this, "Información de la empresa", Toast.LENGTH_SHORT).show()
        }

        // Tipo de donaciones
        buttonBooks.setOnClickListener {
            Toast.makeText(this, "Donación: Libros", Toast.LENGTH_SHORT).show()
        }
        buttonMoney.setOnClickListener {
            Toast.makeText(this, "Donación: Dinero", Toast.LENGTH_SHORT).show()
        }

        // Redes sociales / transporte
        buttonInstagram.setOnClickListener {
            Toast.makeText(this, "Instagram de la empresa", Toast.LENGTH_SHORT).show()
        }
        buttonCar.setOnClickListener {
            Toast.makeText(this, "Información sobre transporte", Toast.LENGTH_SHORT).show()
        }

        // Editar perfil
        textEditProfile.setOnClickListener {
            Toast.makeText(this, "Editar perfil seleccionado", Toast.LENGTH_SHORT).show()
        }

        // Cambio de idioma
        buttonChangeLanguage.setOnClickListener {
            Toast.makeText(this, "Cambiar idioma seleccionado", Toast.LENGTH_SHORT).show()
        }
    }
}
