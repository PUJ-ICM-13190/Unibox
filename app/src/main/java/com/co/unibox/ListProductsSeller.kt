package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ListProductsSeller : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_products_seller)

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
        btnAgregar.setOnClickListener {
            val intent = Intent(this, AddProductSellerActivity::class.java)
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
            val intent = Intent(this, EditProductSeller::class.java)
            startActivity(intent)
        }
        btnEditarLimon.setOnClickListener {
            val intent = Intent(this, EditProductSeller::class.java)
            startActivity(intent)
        }
        btnEditarLulo.setOnClickListener {
            val intent = Intent(this, EditProductSeller::class.java)
            startActivity(intent)
        }
        btnEditarOreo.setOnClickListener {
            val intent = Intent(this, EditProductSeller::class.java)
            startActivity(intent)
        }
        btnEditarMango.setOnClickListener {
            val intent = Intent(this, EditProductSeller::class.java)
            startActivity(intent)
        }
        btnEditarTira.setOnClickListener {
            val intent = Intent(this, EditProductSeller::class.java)
            startActivity(intent)
        }
    }
}
