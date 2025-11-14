package com.example.screensrobe

import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController, modifier: Modifier = Modifier) {
    val borderColor = Color(0xFF59C1B8)
    val backgroundGray = Color(0xFFF2F2F2)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE9F8F5)) // Fondo verde claro
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Botón de retroceso
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { navController?.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.flecha), // tu ícono de flecha
                        contentDescription = "Back",
                        tint = borderColor,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Imagen de perfil
            Image(
                painter = painterResource(id = R.drawable.gojo), // reemplaza con tu imagen
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(3.dp, borderColor, CircleShape)
            )

            Spacer(Modifier.height(16.dp))

            // Nombre y título
            ProfileField(label = "Roberto", backgroundColor = backgroundGray, borderColor = borderColor)
            ProfileField(label = "Ing.", backgroundColor = backgroundGray)

            Spacer(Modifier.height(8.dp))

            // Edad y ocupación
            ProfileField(label = "20 Años", backgroundColor = backgroundGray, borderColor = borderColor)
            ProfileField(label = "Ocupacion", backgroundColor = backgroundGray)
            ProfileField(label = "Estudiante", backgroundColor = backgroundGray)

            Spacer(Modifier.height(24.dp))

            // Editar perfil
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clickable { /* acción editar */ }
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.lapiz_de_color), // ícono lápiz
                    contentDescription = "Edit",
                    tint = borderColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    "Editar perfil",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(16.dp))

            // Botón cambiar idioma
            Button(
                onClick = { /* acción cambiar idioma */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = backgroundGray
                ),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(48.dp)
            ) {
                Text(
                    "Change language",
                    color = Color.Black,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ProfileField(
    label: String,
    backgroundColor: Color,
    borderColor: Color = Color.Transparent
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .border(
                width = if (borderColor != Color.Transparent) 1.5.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 10.dp, horizontal = 12.dp)
    ) {
        Text(
            text = label,
            color = Color.Black,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }

    Spacer(Modifier.height(8.dp))
}
/*
@Composable
fun LanguageButton() {
    val context = LocalContext.current
    var currentLang by remember { mutableStateOf("es") }

    Button(
        onClick = {
            val newLang = if (currentLang == "es") "en" else "es"
            currentLang = newLang

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                changeLanguage(context, newLang)
            } else {
                changeLanguageLegacy(context, newLang)
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE5E5E5)
        ),
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(48.dp)
    ) {
        Text(
            text = if (currentLang == "es") "Cambiar a Inglés" else "Switch to Spanish",
            color = Color.Black,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

fun changeLanguage(context: Context, lang: String) {
    val appLocale = LocaleList(Locale(lang))
    AppCompatDelegate.setApplicationLocales(appLocale)
}
@Suppress("DEPRECATION")
fun changeLanguageLegacy(context: Context, lang: String) {
    val locale = Locale(lang)
    Locale.setDefault(locale)

    val config = context.resources.configuration
    config.setLocale(locale)
    context.resources.updateConfiguration(config, context.resources.displayMetrics)
}

*/
