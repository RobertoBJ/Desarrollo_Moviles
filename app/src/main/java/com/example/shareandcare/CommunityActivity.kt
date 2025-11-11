package com.example.shareandcare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class CommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community)

        Toast.makeText(this, "Bienvenido a la Comunidad", Toast.LENGTH_SHORT).show()
    }
}