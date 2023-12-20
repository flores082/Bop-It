package com.example.flores.bop_it

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Presentacion : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presentacion)

        val menu: Button = findViewById(R.id.button9)
        mediaPlayer = MediaPlayer.create(this, R.raw.precionar)

        menu.setOnClickListener {
            mediaPlayer.start()
            val intent = Intent(this@Presentacion, Menu::class.java)
            startActivity(intent)
            finish()
        }
    }
}