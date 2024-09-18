package com.co.unibox

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditProductSeller : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private fun configureBackSave() {
        val btnSave = findViewById<Button>(R.id.btn_save)
        btnSave.setOnClickListener {
            val intent = Intent(this, EditProductSeller::class.java)
            startActivity(intent)
        }
    }
}