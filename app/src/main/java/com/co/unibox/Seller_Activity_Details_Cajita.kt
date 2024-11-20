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
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var spinnerProductos: Spinner
    private lateinit var btnUbicacion: Button
    private lateinit var btnGuardar: Button

    private var cajaNombre: String? = null // Nombre único de la cajita
    private var userLat: Double = 0.0
    private var userLon: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_details_cajita)

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Recibir datos de la actividad anterior
        cajaNombre = intent.getStringExtra("nombre")
        val descripcion = intent.getStringExtra("descripcion") ?: "Sin descripción"
        val latitud = intent.getDoubleExtra("latitud", 0.0)
        val longitud = intent.getDoubleExtra("longitud", 0.0)

        if (cajaNombre == null) {
            Toast.makeText(this, "Error: Nombre de la caja no encontrado", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Mostrar los datos en la interfaz
        val txtNombreCaja = findViewById<TextView>(R.id.txt_nombre_caja)
        val edtDescripcionCaja = findViewById<EditText>(R.id.edt_descripcion_caja)
        txtNombreCaja.text = cajaNombre
        edtDescripcionCaja.setText(descripcion)

        // Inicializar elementos de la UI
        spinnerProductos = findViewById(R.id.spinner_productos)
        btnUbicacion = findViewById(R.id.btn_ubicacion)
        btnGuardar = findViewById(R.id.btn_guardar)

        // Configurar botón de ubicación para abrir el mapa
        btnUbicacion.setOnClickListener {
            val intent = Intent(this, MapaCajitaActivity::class.java).apply {
                putExtra("latitud", latitud)
                putExtra("longitud", longitud)
            }
            startActivity(intent)
        }

        // Cargar productos del usuario en el Spinner
        cargarProductosUsuario()

        // Configurar botón de guardar
        btnGuardar.setOnClickListener {
            guardarDetalles()
        }

        // Configurar sensores
        configurarSensor()
    }

    private fun configurarSensor() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun cargarProductosUsuario() {
        val productos = mutableListOf<String>()

        if (cajaNombre == null) {
            Toast.makeText(this, "Error: Nombre de la cajita no encontrado", Toast.LENGTH_SHORT).show()
            return
        }

        // Consultar el usuario asociado a la cajita
        database.child("cajitas").child(cajaNombre!!).child("usuario").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userId = snapshot.getValue(String::class.java)

                if (userId == null) {
                    Toast.makeText(this@Seller_Activity_Details_Cajita, "No se encontró el usuario asociado a esta cajita", Toast.LENGTH_SHORT).show()
                    return
                }

                // Consultar los productos del usuario en el nodo products/{userId}
                database.child("products").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(productSnapshot: DataSnapshot) {
                        productos.clear()
                        for (productoSnapshot in productSnapshot.children) {
                            // Leer el atributo "name" de cada producto
                            val nombreProducto = productoSnapshot.child("name").getValue(String::class.java)
                            if (nombreProducto != null) {
                                productos.add(nombreProducto)
                            }
                        }

                        if (productos.isEmpty()) {
                            Toast.makeText(this@Seller_Activity_Details_Cajita, "Este usuario no tiene productos asociados", Toast.LENGTH_SHORT).show()
                        }

                        // Llenar el Spinner con los productos
                        val adapter = ArrayAdapter(this@Seller_Activity_Details_Cajita, android.R.layout.simple_spinner_item, productos)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinnerProductos.adapter = adapter
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@Seller_Activity_Details_Cajita, "Error al cargar productos: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Seller_Activity_Details_Cajita, "Error al obtener el usuario de la cajita: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }



    private fun guardarDetalles() {
        val edtDescripcionCaja = findViewById<EditText>(R.id.edt_descripcion_caja)
        val edtCantidadProductos = findViewById<EditText>(R.id.edt_cantidad_productos)
        val descripcion = edtDescripcionCaja.text.toString()
        val cantidad = edtCantidadProductos.text.toString().toIntOrNull() ?: 0

        val productoSeleccionado = spinnerProductos.selectedItem?.toString()
        if (productoSeleccionado == null) {
            Toast.makeText(this, "Selecciona un producto", Toast.LENGTH_SHORT).show()
            return
        }

        // Guardar los detalles en Firebase
        cajaNombre?.let { nombre ->
            val cajaRef = database.child("cajitas").child(nombre)
            cajaRef.updateChildren(
                mapOf(
                    "descripcion" to descripcion,
                    "cantidad" to cantidad,
                    "producto" to productoSeleccionado
                )
            ).addOnSuccessListener {
                Toast.makeText(this, "Detalles guardados exitosamente", Toast.LENGTH_SHORT).show()
                finish()

            }.addOnFailureListener {
                Toast.makeText(this, "Error al guardar detalles", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // Lógica del sensor (se mantiene igual)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onPause() {
        super.onPause()
        // Detener la escucha del sensor cuando la actividad esté pausada
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        // Registrar de nuevo el sensor cuando la actividad se reanude
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
}
