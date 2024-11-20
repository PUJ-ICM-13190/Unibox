package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class Seller_Activity_Edit_Product : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_edit_product)
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
            val intent = Intent(this, Seller_Activity_Edit_Product::class.java)
            startActivity(intent)
        }
    }
}