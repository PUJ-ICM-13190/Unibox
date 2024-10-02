package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PayClientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopper_pay)

        // Botón de ir a la página principal
        val btn_Home = findViewById<ImageView>(R.id.btn_Home)
        btn_Home.setOnClickListener {
            val intent = Intent(this, HomeClientActivity::class.java)
            startActivity(intent) // Inicia la actividad de HomeClient
        }

        // Botón de WhatsApp
        val btnwhat = findViewById<ImageView>(R.id.btnwhat)
        btnwhat.setOnClickListener {
            val intent = Intent(this, WhatsAppClientActivity::class.java)
            startActivity(intent) // Inicia la actividad de WhatsAppClient
        }
    }
}
