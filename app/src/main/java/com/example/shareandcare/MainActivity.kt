package com.example.shareandcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnProfile = findViewById<Button>(R.id.btn_go_to_profile)
        val btnCompany = findViewById<Button>(R.id.btn_go_to_company)
        val btnDonations = findViewById<Button>(R.id.btn_go_to_donations) // NUEVA LÍNEA

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        btnCompany.setOnClickListener {
            val intent = Intent(this, CompanyProfileActivity::class.java)
            startActivity(intent)
        }

        btnDonations.setOnClickListener { // NUEVO BLOQUE
            val intent = Intent(this, DonationsActivity::class.java)
            startActivity(intent)
        }
        val btnHome = findViewById<Button>(R.id.btn_go_to_home)

        btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        val btnCommunity = findViewById<Button>(R.id.btn_go_to_community)

        btnCommunity.setOnClickListener {
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }
        // Busca el botón de tendencias (asegúrate de que existe en tu XML)
        val btnTrendsNav = findViewById<Button>(R.id.btn_go_to_trends_navigation)

        btnTrendsNav.setOnClickListener {
            val intent = Intent(this, TrendsNavigationActivity::class.java)
            startActivity(intent)
        }
    }
}