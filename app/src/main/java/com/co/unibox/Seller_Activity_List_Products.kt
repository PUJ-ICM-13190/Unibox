package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect.TAG

class Seller_Activity_List_Products : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_list_products)

        configureBackButton()
        configureAddButton()
        configureEditButton()
    }

    private fun configureBackButton() {
        val btnVolver = findViewById<ImageButton>(R.id.btn_back)
        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun configureAddButton() {
        val btnAgregar = findViewById<ImageButton>(R.id.btn_add)
        if (btnAgregar == null) {
            Log.e(TAG, "Add button not found in layout")
            return
        }
        btnAgregar.setOnClickListener {
            Log.d(TAG, "Add button clicked")
            val intent = Intent(this, Seller_Activity_Add_Product::class.java)
            Log.d(TAG, "Navigating to AddProductSellerActivity")
            startActivity(intent)
        }
    }

    private fun configureEditButton() {
        val btnEditarMaracuya = findViewById<ImageButton>(R.id.btn_edit_image)
        val btnEditarLimon = findViewById<ImageButton>(R.id.btn_edit_image_limon)
        val btnEditarLulo = findViewById<ImageButton>(R.id.btn_edit_image_lulo)
        val btnEditarOreo = findViewById<ImageButton>(R.id.btn_edit_image_oreo)
        val btnEditarMango = findViewById<ImageButton>(R.id.btn_edit_image_mango)
        val btnEditarTira = findViewById<ImageButton>(R.id.btn_edit_image_tiramisu)

        btnEditarMaracuya.setOnClickListener {
            val intent = Intent(this, Seller_Activity_Edit_Product::class.java)
            startActivity(intent)
        }
        btnEditarLimon.setOnClickListener {
            val intent = Intent(this, Seller_Activity_Edit_Product::class.java)
            startActivity(intent)
        }
        btnEditarLulo.setOnClickListener {
            val intent = Intent(this, Seller_Activity_Edit_Product::class.java)
            startActivity(intent)
        }
        btnEditarOreo.setOnClickListener {
            val intent = Intent(this, Seller_Activity_Edit_Product::class.java)
            startActivity(intent)
        }
        btnEditarMango.setOnClickListener {
            val intent = Intent(this, Seller_Activity_Edit_Product::class.java)
            startActivity(intent)
        }
        btnEditarTira.setOnClickListener {
            val intent = Intent(this, Seller_Activity_Edit_Product::class.java)
            startActivity(intent)
        }
    }
}
