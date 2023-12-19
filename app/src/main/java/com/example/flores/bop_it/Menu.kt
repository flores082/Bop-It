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

        val intrucciones: Button = findViewById(R.id.button)

        intrucciones.setOnClickListener{
            val intent = Intent(this@Menu, Preferencias::class.java)
            startActivity(intent)
            finish()
        }
    }
}