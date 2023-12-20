package com.example.flores.bop_it

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Menu : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private var mediaPlayerA: MediaPlayer? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cargar la canción desde los recursos raw
        mediaPlayerA = MediaPlayer.create(this, R.raw.samba)
        mediaPlayerA?.start()

        mediaPlayer = MediaPlayer.create(this, R.raw.precionar)

        val jugar: Button = findViewById(R.id.button2)
        val puntaje: Button = findViewById(R.id.button7)
        val preferencias: Button = findViewById(R.id.button)
        val presentacion: Button = findViewById(R.id.button5)
        val salir: Button = findViewById(R.id.button6)

        jugar.setOnClickListener{
            mediaPlayer.start()
            mediaPlayerA?.stop()
            val intent = Intent(this@Menu, Juego::class.java)
            startActivity(intent)
            finish()
        }
        puntaje.setOnClickListener{
            mediaPlayer.start()
            val intent = Intent(this@Menu, Puntaje_Guardado::class.java)
            startActivity(intent)
            finish()
        }
        preferencias.setOnClickListener{
            mediaPlayer.start()
            val intent = Intent(this@Menu, Preferencias::class.java)
            startActivity(intent)
            finish()
        }
        presentacion.setOnClickListener{
            mediaPlayer.start()
            val intent = Intent(this@Menu, Presentacion::class.java)
            startActivity(intent)
            finish()
        }
        salir.setOnClickListener{
            mediaPlayer.start()
            // Detener la reproducción
            mediaPlayerA?.stop()
            mediaPlayerA?.release()
            finishAffinity()
            // Cierra la aplicación por completo
            System.exit(0)
        }

        // Configurar el OnCompletionListener para repetir la canción cuando termine
        mediaPlayerA?.setOnCompletionListener {
            mediaPlayerA?.start()
        }
    }
}