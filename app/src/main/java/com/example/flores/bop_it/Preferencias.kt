package com.example.flores.bop_it

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.flores.bop_it.Utilidad.MyPreferences

class Preferencias : AppCompatActivity() {

    private val myPreferences by lazy { MyPreferences(this) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)

        val verdadero: Button = findViewById(R.id.button3)
        val falso: Button = findViewById(R.id.button4)

        verdadero.setOnClickListener {
            // Cambiar el valor del booleano y guardarlo
            myPreferences.setBoolean("feature_enabled", true)
            val intent = Intent(this@Preferencias, Menu::class.java)
            startActivity(intent)
            finish()
        }
        falso.setOnClickListener {
            // Cambiar el valor del booleano y guardarlo
            myPreferences.setBoolean("feature_enabled", false)
            val intent = Intent(this@Preferencias, Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}