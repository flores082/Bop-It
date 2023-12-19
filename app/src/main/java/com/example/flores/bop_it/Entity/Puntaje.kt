package com.example.flores.bop_it.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tabla_puntajes")
data class Puntaje(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val fecha: String,
    val hora: String,
    val puntaje: Int
): Serializable