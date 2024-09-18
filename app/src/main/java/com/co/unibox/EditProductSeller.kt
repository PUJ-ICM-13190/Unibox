package com.co.unibox

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditProductSeller : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_product_seller)

        configureBackButton()
        configureBackSave()

    }

    private fun configureBackButton() {
        val btnVolver = findViewById<ImageButton>(R.id.btn_back)
        btnVolver.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("WrongViewCast")
    private fun configureBackSave() {
        val btnGuardar = findViewById<ImageButton>(R.id.btn_save)
        btnGuardar.setOnClickListener {
            finish()
        }
    }
}