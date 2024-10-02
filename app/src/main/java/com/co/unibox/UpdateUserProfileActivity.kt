package com.co.unibox

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class UpdateUserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopper_update_data)

        // Botón de retroceso
        val btn_regresar = findViewById<ImageView>(R.id.btn_regresar)
        btn_regresar.setOnClickListener {
            finish() // Vuelve atrás cuando se hace clic en el ImageView
        }
    }
}
