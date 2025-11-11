package com.example.shareandcare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val optionEditProfile = findViewById<LinearLayout>(R.id.option_edit_profile)
        val optionLanguage = findViewById<LinearLayout>(R.id.option_language)

        optionEditProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        optionLanguage.setOnClickListener {
            showLanguageDialog()
        }
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("Español", "English")
        AlertDialog.Builder(this)
            .setTitle("Selecciona idioma")
            .setItems(languages) { dialog, which ->
                when (which) {
                    0 -> showMessage("Idioma cambiado a Español")
                    1 -> showMessage("Language changed to English")
                }
            }
            .show()
    }

    private fun showMessage(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}