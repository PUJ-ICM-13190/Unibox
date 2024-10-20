package com.co.unibox

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class Seller_Activity_Details_Cajita : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var lastX: Float = 0.0f
    private var cajaId: Int = 1 // Empezamos con la primera caja
    private val totalCajas: Int = 5 // Número fijo de cajas (quemado)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_details_cajita)

        // Inicializamos el sensor del acelerómetro
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Verificar si el acelerómetro está disponible en el dispositivo
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        } else {
            // Si no está disponible, mostrar un mensaje al usuario
            Toast.makeText(this, "Acelerómetro no disponible", Toast.LENGTH_SHORT).show()
        }

        // Botón de retroceso
        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish() // Vuelve atrás
        }

        // Botón de Ubicación: Navega a la pantalla de Ubicación
        val btnUbicacion = findViewById<Button>(R.id.btn_ubicacion)
        btnUbicacion.setOnClickListener {
            val intent = Intent(this, Seller_Activity_Location::class.java)
            startActivity(intent)
        }

        // Botón de Productos: Navega a la pantalla de Productos
        val btnProductos = findViewById<Button>(R.id.btn_productos)
        btnProductos.setOnClickListener {
            val intent = Intent(this, Seller_Activity_Products::class.java)
            startActivity(intent)
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]

        // Detectar si hay un movimiento de izquierda a derecha o derecha a izquierda
        if (lastX - x > 5) {
            cambiarCaja(cajaId - 1)  // Moverse a la caja anterior
        } else if (x - lastX > 5) {
            cambiarCaja(cajaId + 1)  // Moverse a la caja siguiente
        }

        lastX = x
    }

    private fun cambiarCaja(nuevaCajaId: Int) {
        // Verificar si la nueva caja está dentro de los límites
        if (nuevaCajaId >= 1 && nuevaCajaId <= totalCajas) {
            // Actualizar el ID de la caja
            cajaId = nuevaCajaId
            // Recargar la actividad con los detalles de la nueva caja
            val intent = Intent(this, Seller_Activity_Details_Cajita::class.java)
            intent.putExtra("cajaId", cajaId)  // Pasar el nuevo ID de la caja
            startActivity(intent)
            finish() // Cerrar la actividad actual para evitar acumulación en el stack
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No es necesario manejar esto en este caso
    }

    override fun onPause() {
        super.onPause()
        // Detener el sensor cuando la actividad está en pausa
        sensorManager.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        // Registrar el sensor nuevamente cuando la actividad esté en uso
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }
}
