package com.co.unibox

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Seller_Activity_Details_Cajita : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lastX: Float = 0.0f
    private var cajaId: Int = 1 // Caja actual (se recibe desde el Intent)
    private val totalCajas: Int = 3 // Cambia este valor según el número total de cajitas
    private var lastMovementTime: Long = 0 // Control de tiempo para retrasar movimientos

    // Umbral para definir un movimiento válido
    private val movementThreshold = 4.0f // Ajuste del umbral para evitar movimientos falsos
    private val movementCooldown = 1500 // 1500 ms = 1.5 segundos entre movimientos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_details_cajita)

        // Recibir el ID de la caja desde la actividad anterior
        cajaId = intent.getIntExtra("cajaId", 1)

        // Inicializar el sensor del acelerómetro
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        // Mostrar la información de la caja actual
        mostrarInformacionCaja(cajaId)

        // Configurar el botón de retroceso
        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish() // Cierra la actividad actual y regresa a la anterior
        }
    }

    // Mostrar la información de la caja actual (nombre, descripción, etc.)
    private fun mostrarInformacionCaja(cajaId: Int) {
        val nombreCaja = findViewById<TextView>(R.id.txt_nombre_caja)
        val descripcionCaja = findViewById<EditText>(R.id.edt_descripcion_caja)
        val cantidadProductos = findViewById<EditText>(R.id.edt_cantidad_productos)
        val ventasDelDia = findViewById<TextView>(R.id.txt_ventas_del_dia)

        // Actualizar con la información real de la cajita
        when (cajaId) {
            1 -> {
                nombreCaja.text = "Cajita #1"
                descripcionCaja.setText("Descripción de la cajita 1")
                cantidadProductos.setText("Cant. productos: 10")
                ventasDelDia.text = "Ventas del día: 5"
            }
            2 -> {
                nombreCaja.text = "Cajita #2"
                descripcionCaja.setText("Descripción de la cajita 2")
                cantidadProductos.setText("Cant. productos: 12")
                ventasDelDia.text = "Ventas del día: 7"
            }
            3 -> {
                nombreCaja.text = "Cajita #3"
                descripcionCaja.setText("Descripción de la cajita 3")
                cantidadProductos.setText("Cant. productos: 8")
                ventasDelDia.text = "Ventas del día: 3"
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val currentTime = System.currentTimeMillis()

            // Solo permitir un movimiento si ha pasado el tiempo de cooldown
            if (currentTime - lastMovementTime > movementCooldown) {
                val x = it.values[0]

                // Invertir la lógica:
                // Izquierda (disminuir caja) ahora es si x > lastX
                if (x - lastX > movementThreshold) {
                    // Movimiento hacia la izquierda, disminuir caja
                    if (cajaId > 1) {
                        cambiarCaja(cajaId - 1)
                    } else {
                        Toast.makeText(this, "No hay más cajitas a la izquierda", Toast.LENGTH_SHORT).show()
                    }
                    lastMovementTime = currentTime
                }
                // Derecha (aumentar caja) es si x < lastX
                else if (lastX - x > movementThreshold) {
                    // Movimiento hacia la derecha, aumentar caja
                    if (cajaId < totalCajas) {
                        cambiarCaja(cajaId + 1)
                    } else {
                        Toast.makeText(this, "No hay más cajitas a la derecha", Toast.LENGTH_SHORT).show()
                    }
                    lastMovementTime = currentTime
                }

                lastX = x // Actualizar lastX con el valor actual del sensor
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No necesitamos implementar esto para este caso
    }

    private fun cambiarCaja(nuevaCajaId: Int) {
        if (nuevaCajaId in 1..totalCajas) {
            cajaId = nuevaCajaId
            val intent = Intent(this, Seller_Activity_Details_Cajita::class.java)
            intent.putExtra("cajaId", cajaId)
            startActivity(intent)
            finish() // Terminar la actividad actual para que no se pueda regresar con el botón de atrás
        }
    }

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

