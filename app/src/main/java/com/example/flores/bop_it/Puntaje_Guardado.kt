package com.example.flores.bop_it

import android.annotation.SuppressLint
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.flores.bop_it.Dao.PuntajeDatabase
import com.example.flores.bop_it.Entity.Puntaje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Puntaje_Guardado : AppCompatActivity() {

    private lateinit var listaChistes: ListView
    private lateinit var List: MutableList<Puntaje>
    private lateinit var adapter : ArrayAdapter<String>
    private lateinit var baseDatos: PuntajeDatabase

    val juegoInstancia = Juego()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_puntaje_guardado)

        baseDatos = Room.databaseBuilder(
            applicationContext,
            PuntajeDatabase::class.java, "database"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

        val ProdDao = baseDatos.puntajeDao()

        val users: List<Puntaje> = ProdDao.getAll()

        listaChistes = findViewById(R.id.ListView)

        List = users.toMutableList()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, List.map { it.nombre })

        listaChistes.adapter = adapter


        listaChistes.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var selectedItem = parent.getItemAtPosition(position) as String

            // Aquí accedes al elemento seleccionado y puedes realizar acciones con él
            editProducto(selectedItem)
        }

    }

    fun editProducto(itemSeleccionado: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Chiste?")

        builder.setPositiveButton("Eliminar Chiste") { dialog, _ ->
            val ProdDao = baseDatos.puntajeDao()
            ProdDao.delete(ProdDao.findByName(itemSeleccionado))
            Log.i("AAAAA", "ELIMINADO")
            val users: List<Puntaje> = ProdDao.getAll()

            //chists.clear()
            adapter.clear()
            adapter.addAll(users.toMutableList().map { it.nombre })
            adapter.notifyDataSetChanged()

            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->

            dialog.dismiss()
            // Puedes realizar acciones adicionales al hacer clic en Cancelar
        }


        val dialog = builder.create()
        dialog.show()
    }

}