package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class DetailsCajitaSeller : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_cajita_seller)

        // Configurar el botón de retroceso
        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish() // Volver atrás
        }

        // Botón de Ubicación: Navega a la pantalla de Ubicación
        val btnUbicacion = findViewById<Button>(R.id.btn_ubicacion)
        btnUbicacion.setOnClickListener {
            val intent = Intent(this, ActivityLocationSeller::class.java)
            startActivity(intent)
        }

        // Botón de Productos: Navega a la pantalla de Productos
        val btnProductos = findViewById<Button>(R.id.btn_productos)
        btnProductos.setOnClickListener {
            val intent = Intent(this, ActivityProductsSeller::class.java)
            startActivity(intent)
        }

        // Aquí puedes agregar más funcionalidad si lo necesitas
    }
}
