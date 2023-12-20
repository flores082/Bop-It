package com.example.flores.bop_it

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.util.Random
import androidx.room.Room
import com.example.flores.bop_it.Dao.PuntajeDatabase
import com.example.flores.bop_it.Entity.Puntaje
import android.content.Context
import android.view.View


class Juego : AppCompatActivity(),
    GestureDetector.OnGestureListener,
    GestureDetector.OnDoubleTapListener, SensorEventListener {


    private lateinit var TextView: TextView
    private lateinit var TextViewP: TextView
    private lateinit var P: TextView

    private lateinit var gestureDetector: GestureDetector
    private lateinit var Accelerometro: Sensor

    private var a: String = ""
    private var b: String = ""
    private var c: String = ""
    private var d: String = ""
    private var e: String = ""

    private var random = Random()
    var palabra = arrayOf(a, b, c, d, e)

    /*<string name="a">Agita</string>
    <string name="b">Presiona una vez</string>
    <string name="c">Manten Presionado</string>
    <string name="d">Desplazar</string>
    <string name="e">Lanzamiento</string>*/

    private var palabraActual: String = ""
    private var juegoTerminado = false
    private var Accion = false
    private var Puntaje: Int = 7

    private var handler = Handler(Looper.getMainLooper())
    val Tiempo_palabra= 1000L
    private var N =1000

    private var temporizador: CountDownTimer? = null

    //var baseDatos = Room.databaseBuilder(applicationContext, PuntajeDatabase::class.java, "database").allowMainThreadQueries().build()

    private lateinit var baseDatos: PuntajeDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_juego)

        TextView = findViewById(R.id.Palabras)
        TextViewP = findViewById(R.id.textView3)
        P = findViewById(R.id.textView5)

        a = getString(R.string.a)
        b = getString(R.string.b)
        c = getString(R.string.c)
        d = getString(R.string.d)
        e = getString(R.string.e)

        palabra = arrayOf(a, b, c, d, e)
        TextViewP.text = getString(R.string.P)

        gestureDetector = GestureDetector(this, this)
        gestureDetector.setOnDoubleTapListener(this)

        //sensor
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        baseDatos = Room.databaseBuilder(applicationContext, PuntajeDatabase::class.java, "database").allowMainThreadQueries().build()


        mostrarPalabra()
        Play()

        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        actionBar?.hide()
    }
    fun reiniciar(){
        temporizador?.cancel()
        Play()
    }
    fun Play() {
        temporizador=object : CountDownTimer(N.toLong(), 1000) {
            // 60000 milisegundos (60 segundos), 1000 milisegundos (1 segundo) entre ticks
            override fun onTick(millisUntilFinished: Long) {
                // Código que se ejecuta en cada tick del temporizador
            }

            override fun onFinish() {
                InCorrecto()
            }
        }.start()
    }

    private fun mostrarPalabra() {
        Accion=true //funciona para cuando se acierte una ves no le de mas puntos de lo normal
        TextView.text = getString(R.string.esperando)
        P.text = Puntaje.toString()

        handler.postDelayed({
            if (!juegoTerminado) {
                Accion = false
                val numeroAleatorio = random.nextInt(palabra.size)
                palabraActual = when (numeroAleatorio) {
                    0 ->a
                    1 ->b
                    2->c
                    3->d
                    else -> e
                }
                TextView.text = palabraActual
            }},Tiempo_palabra)
    }
    fun Correcto() {
        Accion=true
        Puntaje++
        reiniciar()
        mostrarPalabra()
    }
    fun InCorrecto() {
        if(!juegoTerminado) {
            TextView.text = getString(R.string.D)
            juegoTerminado = true
        }
        mostrarDialogoIngresoDatos()
    }


    override fun onResume() {
        super.onResume()
        val sm = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER)

        if (sensors.isNotEmpty()) {
            Accelerometro = sensors[0]
            sm.registerListener(this, Accelerometro, SensorManager.SENSOR_DELAY_GAME)
        }
    }
    override fun onPause() {
        val mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensorManager.unregisterListener(this, Accelerometro)
        super.onPause()
    }
    override fun onStop() {
        val mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensorManager.unregisterListener(this, Accelerometro)
        super.onStop()
    }

    override fun onSensorChanged(event: SensorEvent) {
        //X.text = "X = ${event.values[0]}"
        //Y.text = "Y = ${event.values[1]}"
        //Z.text = "Z = ${event.values[2]}"
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val magnitudAceleracion = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
        val umbralMovimientoRapido = 18.0
        if(!Accion) {
            if (magnitudAceleracion > umbralMovimientoRapido && palabraActual == a) {
                Correcto()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onDown(e: MotionEvent): Boolean {

        return true
    }

    override fun onShowPress(e: MotionEvent) {

    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        if(!Accion) {
            if (palabraActual == b) {
                Correcto()
            }
        }
        return true
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        if(!Accion){
            if(palabraActual==d) {
                Correcto()
            }
        }
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        if (!Accion){
            if (palabraActual == c) {
                Correcto()
            }
        }
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if(!Accion) {
            if (palabraActual == e) {
                Correcto()
            }
        }
        return true
    }

    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return true
    }

    override fun onDoubleTap(e: MotionEvent): Boolean {
        return true
    }

    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return true
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    @SuppressLint("MissingInflatedId")
    private fun mostrarDialogoIngresoDatos(){
        val dialogoView = layoutInflater.inflate(R.layout.dialog_ingreso_datos, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogoView)
            .setTitle("Ingresar Datos")

        // Configurar el diálogo como modal y deshabilitar el cierre al tocar fuera
        builder.setCancelable(false)

        val dialogo = builder.create()
        dialogo.show()

        // Acceder a los elementos de interfaz de usuario dentro del diálogo
        val editTextNombre = dialogoView.findViewById<EditText>(R.id.editTextNombre)
        val editTextFecha = dialogoView.findViewById<EditText>(R.id.editTextFecha)
        val editTextHora = dialogoView.findViewById<EditText>(R.id.editTextHora)
        val textViewPuntaje = dialogoView.findViewById<TextView>(R.id.textViewPuntaje)
        val botonCancelar = dialogoView.findViewById<Button>(R.id.botonCancelar)
        val botonGuardar = dialogoView.findViewById<Button>(R.id.botonGuardar)

        // Mostrar el puntaje actual
        textViewPuntaje.text = getString(R.string.PuntajeA)+":$Puntaje"

        // Configurar el comportamiento de los botones
        botonCancelar.setOnClickListener {
            val intent = Intent(this@Juego, Menu::class.java)
            startActivity(intent)
            finish()
            dialogo.dismiss() // Cerrar el diálogo al hacer clic en Cancelar
        }

        botonGuardar.setOnClickListener {
            // Obtener datos ingresados por el usuario
            val n = editTextNombre.text.toString()
            val f = editTextFecha.text.toString()
            val h = editTextHora.text.toString()
            val p = Puntaje

            //val puntajeObj = Puntaje(nombre = n, fecha = f, hora = h, puntaje = p)
            val ProdDao = baseDatos.puntajeDao()
            val produ = Puntaje(ProdDao.getAll().size.toLong()+1,n,f,h,p)
            ProdDao.insertAll(produ)

            val intent = Intent(this@Juego, Menu::class.java)
            startActivity(intent)
            finish()
            // Cerrar el diálogo
            dialogo.dismiss()
        }
    }

}