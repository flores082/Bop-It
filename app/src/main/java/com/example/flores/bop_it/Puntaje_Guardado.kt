package com.example.flores.bop_it

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.flores.bop_it.Dao.PuntajeDatabase
import com.example.flores.bop_it.Entity.Puntaje
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Puntaje_Guardado : AppCompatActivity() {

    private lateinit var lista: ListView
    private lateinit var List: MutableList<Puntaje>
    private lateinit var adapter : ArrayAdapter<Puntaje>
    private lateinit var baseDatos: PuntajeDatabase

    private lateinit var textView: TextView

    private lateinit var mediaPlayer: MediaPlayer

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

        lista = findViewById(R.id.ListView)

        List = users.toMutableList()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, List)

        lista.adapter = adapter

        adapter.notifyDataSetChanged()
        puntajeMayor()

        lista.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            var selectedItem = parent.getItemAtPosition(position) as Puntaje
            // Aquí accedes al elemento seleccionado y puedes realizar acciones con él
            editProducto(selectedItem)
        }

        val menu: Button = findViewById(R.id.button8)
        mediaPlayer = MediaPlayer.create(this, R.raw.precionar)

        menu.setOnClickListener {
            mediaPlayer.start()
            val intent = Intent(this@Puntaje_Guardado, Menu::class.java)
            startActivity(intent)
            finish()
        }


    }

    fun editProducto(puntaje: Puntaje) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Eliminar Chiste?")

        builder.setPositiveButton("Eliminar Chiste") { dialog, _ ->
            val ProdDao = baseDatos.puntajeDao()
            ProdDao.delete(ProdDao.findByNombreFechaHora(puntaje.nombre,puntaje.fecha,puntaje.hora)!!)
            Log.i("AAAAA", "ELIMINADO")
            val users: List<Puntaje> = ProdDao.getAll()
            //chists.clear()
            adapter.clear()
            adapter.addAll(users.toMutableList())
            adapter.notifyDataSetChanged()

            puntajeMayor()

            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->

            dialog.dismiss()
            // Puedes realizar acciones adicionales al hacer clic en Cancelar
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun puntajeMayor(){
        textView = findViewById(R.id.textViewPuntajeMayor)

        val maxNombre = List.maxByOrNull { it.puntaje }?.nombre ?: 0
        val maxFecha = List.maxByOrNull { it.puntaje }?.fecha ?: 0
        val maxHora = List.maxByOrNull { it.puntaje }?.hora ?: 0
        val maxPuntaje = List.maxByOrNull { it.puntaje }?.puntaje ?: 0

        textView.text = "Nombre: $maxNombre\nFecha: $maxFecha\nHora: $maxHora\nPuntaje: $maxPuntaje"
    }

}