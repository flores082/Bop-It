package com.example.flores.bop_it

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.flores.bop_it.Utilidad.MyPreferences

class Bienvenida : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        mediaPlayer = MediaPlayer.create(this, R.raw.inicio)
        mediaPlayer.start()
        VerBienvenida()
    }

    fun VerBienvenida() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                mediaPlayer.stop()
                val intent = Intent(this@Bienvenida, Instrucciones::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }.start()
    }
}