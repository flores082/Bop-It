package com.example.flores.bop_it.Dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flores.bop_it.Entity.Puntaje

@Database(entities = [Puntaje::class], version = 1, exportSchema = false)
abstract class PuntajeDatabase: RoomDatabase() {
    abstract fun puntajeDao(): PuntajeDao
}