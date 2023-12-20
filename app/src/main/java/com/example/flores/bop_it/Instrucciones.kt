package com.example.flores.bop_it

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.flores.bop_it.Utilidad.MyPreferences

class Instrucciones : AppCompatActivity() {

    private val myPreferences by lazy { MyPreferences(this) }
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrucciones)

        mediaPlayer = MediaPlayer.create(this, R.raw.precionar)

        // Obtener el valor almacenado
        val isFeatureEnabled = myPreferences.getBoolean("feature_enabled", true)

        val verdadero: Button = findViewById(R.id.button3)
        val falso: Button = findViewById(R.id.button4)

        verdadero.setOnClickListener {
            // Cambiar el valor del booleano y guardarlo
            mediaPlayer.start()
            myPreferences.setBoolean("feature_enabled", isFeatureEnabled)
            val intent = Intent(Intent(this@Instrucciones, Menu::class.java))
            startActivity(intent)
            finish()
        }
        falso.setOnClickListener {
            // Cambiar el valor del booleano y guardarlo
            mediaPlayer.start()
            myPreferences.setBoolean("feature_enabled", !isFeatureEnabled)
            val intent = Intent(this@Instrucciones, Menu::class.java)
            startActivity(intent)
            finish()
        }

        if(!isFeatureEnabled)
        {
            val intent = Intent(this@Instrucciones, Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}