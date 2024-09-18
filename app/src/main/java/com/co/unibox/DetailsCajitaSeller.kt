package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton

class DetailsCajitaSeller : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Establece el layout de la actividad como el archivo XML llamado activity_details_cajita_seller
        setContentView(R.layout.activity_details_cajita_seller)

        // Configura el botón de retroceso
        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            // Finaliza la actividad y regresa a la pantalla anterior
            finish()
        }

        // Configura el botón de "Ubicación"
        val btnUbicacion = findViewById<Button>(R.id.btn_ubicacion)
        btnUbicacion.setOnClickListener {
            // Acción al presionar el botón "Ubicación"
        }

        // Configura el botón de "Productos"
        val btnProductos = findViewById<Button>(R.id.btn_productos)
        btnProductos.setOnClickListener {
            // Acción al presionar el botón "Productos"
        }

        // Configura el botón de "Transacciones"
        val btnTransacciones = findViewById<Button>(R.id.btn_transacciones)
        btnTransacciones.setOnClickListener {
            // Acción al presionar el botón "Transacciones"
        }
    }
}
