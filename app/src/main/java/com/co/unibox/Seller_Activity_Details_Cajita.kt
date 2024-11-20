package com.co.unibox

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Seller_Activity_Details_Cajita : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lastX: Float = 0.0f
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var spinnerProductos: Spinner
    private lateinit var btnUbicacion: Button
    private lateinit var btnGuardar: Button

    private var cajaId: String? = null // ID de la cajita
    private var userLat: Double = 0.0
    private var userLon: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_details_cajita)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Recibir el ID de la caja desde la actividad anterior
        val cajaNombre = intent.getStringExtra("cajaNombre")

        if (cajaNombre == null) {
            Toast.makeText(this, "No se pudo cargar la información de la caja", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Inicializar elementos de la UI
        spinnerProductos = findViewById(R.id.spinner_productos)
        btnUbicacion = findViewById(R.id.btn_ubicacion)
        btnGuardar = findViewById(R.id.btn_guardar)

        // Configurar sensores
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Mostrar detalles de la caja
        mostrarDetallesCajita(cajaNombre)

        // Cargar productos del usuario en el Spinner
        cargarProductosUsuario()

        // Configurar botón de ubicación
        btnUbicacion.setOnClickListener {
            abrirMapaConUbicacion()
        }

        // Configurar botón de guardar
        btnGuardar.setOnClickListener {
            guardarDetalles()
        }
    }

    private fun mostrarDetallesCajita(cajaNombre: String) {
        val txtNombreCaja = findViewById<TextView>(R.id.txt_nombre_caja)
        val edtDescripcionCaja = findViewById<EditText>(R.id.edt_descripcion_caja)
        val edtCantidadProductos = findViewById<EditText>(R.id.edt_cantidad_productos)

        // Consultar Firebase usando el nombre de la caja
        database.child("cajitas").child(cajaNombre).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    txtNombreCaja.text = snapshot.child("nombre").getValue(String::class.java) ?: "Sin nombre"
                    edtDescripcionCaja.setText(snapshot.child("descripcion").getValue(String::class.java) ?: "")
                    edtCantidadProductos.setText(snapshot.child("cantidad").getValue(Int::class.java)?.toString() ?: "0")
                } else {
                    Toast.makeText(this@Seller_Activity_Details_Cajita, "No se encontraron datos para esta caja", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Seller_Activity_Details_Cajita, "Error al cargar detalles: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun cargarProductosUsuario() {
        val productos = mutableListOf<String>()
        val userId = auth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        database.child("users").child(userId).child("productos").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                productos.clear()
                for (productoSnapshot in snapshot.children) {
                    productos.add(productoSnapshot.getValue(String::class.java) ?: "Producto desconocido")
                }

                val adapter = ArrayAdapter(this@Seller_Activity_Details_Cajita, android.R.layout.simple_spinner_item, productos)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerProductos.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Seller_Activity_Details_Cajita, "Error al cargar productos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun abrirMapaConUbicacion() {
        val userId = auth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        database.child("users").child(userId).child("ubicacion").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userLat = snapshot.child("latitud").getValue(Double::class.java) ?: 0.0
                userLon = snapshot.child("longitud").getValue(Double::class.java) ?: 0.0

                val intent = Intent(this@Seller_Activity_Details_Cajita, MapaCajitaActivity::class.java).apply {
                    putExtra("latitud", userLat)
                    putExtra("longitud", userLon)
                }
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Seller_Activity_Details_Cajita, "Error al obtener ubicación: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun guardarDetalles() {
        val edtDescripcionCaja = findViewById<EditText>(R.id.edt_descripcion_caja)
        val edtCantidadProductos = findViewById<EditText>(R.id.edt_cantidad_productos)
        val descripcion = edtDescripcionCaja.text.toString()
        val cantidad = edtCantidadProductos.text.toString().toIntOrNull() ?: 0

        cajaId?.let { id ->
            database.child("cajitas").child(id).updateChildren(
                mapOf(
                    "descripcion" to descripcion,
                    "cantidad" to cantidad
                )
            ).addOnSuccessListener {
                Toast.makeText(this, "Detalles guardados exitosamente", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Error al guardar detalles", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Lógica del sensor (se mantiene igual)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
