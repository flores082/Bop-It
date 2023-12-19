package com.example.flores.bop_it.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.flores.bop_it.Entity.Puntaje
@Dao
interface PuntajeDao {

    @Query("SELECT * FROM tabla_puntajes")
    fun getAll(): List<Puntaje>

    @Query("SELECT * FROM tabla_puntajes WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<Puntaje>

    @Query("SELECT * FROM tabla_puntajes WHERE puntaje LIKE :first " +
            " LIMIT 1")
    fun findByName(first: String): Puntaje

    @Insert
    fun insertAll(vararg productos: Puntaje)

    @Delete
    fun delete(user: Puntaje)

}