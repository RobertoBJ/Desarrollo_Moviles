package com.example.shareandcare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast

class TrendsNavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trends_navigation)

        setupNavigation()
    }

    private fun setupNavigation() {
        val optionTecnologico = findViewById<LinearLayout>(R.id.option_tecnologico_aguascalientes)
        val optionAcademia = findViewById<LinearLayout>(R.id.option_academia_ua)
        val optionKyunity = findViewById<LinearLayout>(R.id.option_kyunity)

        optionTecnologico.setOnClickListener {
            Toast.makeText(this,
                "Tecnológico de Aguascalientes\nInstitución líder en donaciones tecnológicas",
                Toast.LENGTH_LONG).show()
        }

        optionAcademia.setOnClickListener {
            Toast.makeText(this,
                "Academia U.A.\nProgramas académicos de responsabilidad social",
                Toast.LENGTH_LONG).show()
        }

        optionKyunity.setOnClickListener {
            Toast.makeText(this,
                "KYUNITY\nComunidad empresarial de donaciones colaborativas",
                Toast.LENGTH_LONG).show()
        }
    }
}