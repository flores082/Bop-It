package com.example.flores.bop_it

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaFeature
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import com.example.flores.bop_it.Utilidad.MyPreferences

class Preferencias : AppCompatActivity() {

    private val myPreferences by lazy { MyPreferences(this) }
    private lateinit var mediaPlayer: MediaPlayer

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)

        mediaPlayer = MediaPlayer.create(this, R.raw.precionar)

        val verdadero: Button = findViewById(R.id.si)
        val falso: Button = findViewById(R.id.no)
        val menu: Button = findViewById(R.id.M)

        // Obtener el valor actual del booleano
        val featureEnabled = myPreferences.getBoolean("feature_enabled", false)

        // Actualizar los colores de los botones según el valor del booleano
        updateButtonColors(verdadero, falso, featureEnabled)

            verdadero.setOnClickListener {
                // Cambiar el valor del booleano y guardarlo
                mediaPlayer.start()
                myPreferences.setBoolean("feature_enabled", true)

                // Actualizar los colores de los botones
                updateButtonColors(verdadero, falso, true)
            }
            falso.setOnClickListener {
                // Cambiar el valor del booleano y guardarlo
                mediaPlayer.start()
                myPreferences.setBoolean("feature_enabled", false)

                // Actualizar los colores de los botones
                updateButtonColors(verdadero, falso, false)
            }
            menu.setOnClickListener {
                mediaPlayer.start()
                val intent = Intent(this@Preferencias, Menu::class.java)
                startActivity(intent)
                finish()
            }

    }

    private fun updateButtonColors(verdadero: Button, falso: Button, featureEnabled:Boolean) {
        if (featureEnabled){
            verdadero.setBackgroundColor(ContextCompat.getColor(this, R.color.azul))
            falso.setBackgroundColor(ContextCompat.getColor(this, R.color.azul_oscuro))
        } else {
            verdadero.setBackgroundColor(ContextCompat.getColor(this, R.color.azul_oscuro))
            falso.setBackgroundColor(ContextCompat.getColor(this, R.color.azul))
        }
    }
}