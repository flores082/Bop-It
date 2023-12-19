package com.example.flores.bop_it

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Menu : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jugar: Button = findViewById(R.id.button2)
        val puntaje: Button = findViewById(R.id.button7)
        val preferencias: Button = findViewById(R.id.button)
        val presentacion: Button = findViewById(R.id.button5)
        val salir: Button = findViewById(R.id.button6)

        jugar.setOnClickListener{
            val intent = Intent(this@Menu, Juego::class.java)
            startActivity(intent)
            finish()
        }
        puntaje.setOnClickListener{
            val intent = Intent(this@Menu, Puntaje_Guardado::class.java)
            startActivity(intent)
            finish()
        }
        preferencias.setOnClickListener{
            val intent = Intent(this@Menu, Preferencias::class.java)
            startActivity(intent)
            finish()
        }
        presentacion.setOnClickListener{
            val intent = Intent(this@Menu, Presentacion::class.java)
            startActivity(intent)
            finish()
        }
        salir.setOnClickListener{
            finishAffinity()
        }
    }
}