package com.example.shareandcare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Aquí puedes agregar la funcionalidad después
        Toast.makeText(this, "Bienvenido al inicio", Toast.LENGTH_SHORT).show()
    }
}