package com.co.unibox

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Seller_Activity_Location : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_location)

        // Botón de retroceso
        val btnBack = findViewById<ImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            finish() // Vuelve atrás cuando se hace clic en el botón de retroceso
        }
    }
}
