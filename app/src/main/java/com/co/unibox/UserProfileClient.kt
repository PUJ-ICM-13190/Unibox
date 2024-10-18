package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import android.widget.ImageView

class UserProfileClient : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopper_user_profile)

        // Botón de retroceso (este sigue siendo un ImageView)
        val btnRegresar = findViewById<ImageView>(R.id.btn_regresar)
        btnRegresar.setOnClickListener {
            finish() // Vuelve atrás cuando se hace clic en el ImageView
        }

        // Botón de Mi Cuenta (es un ConstraintLayout en el XML)
        val btnMiCuenta = findViewById<Button>(R.id.btn_mi_cuenta)
        btnMiCuenta.setOnClickListener {
            val intent = Intent(this, UpdateUserProfileActivity::class.java)
            startActivity(intent) // Inicia la actividad para actualizar el perfil
        }

        // Botón de Modo Vendedor (es un ConstraintLayout en el XML)
        val btnModoVendedor = findViewById<Button>(R.id.btn_modo_vendedor)
        btnModoVendedor.setOnClickListener {
            val intent = Intent(this, HomeSellerActivity::class.java)
            startActivity(intent) // Inicia la actividad del vendedor
        }
    }
}
