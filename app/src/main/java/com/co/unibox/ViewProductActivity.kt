package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorProductViewGeneralBinding

class ViewProductActivity : AppCompatActivity(){

    private lateinit var binding: CompradorProductViewGeneralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comprador_product_view_general)

        // Verifica si el botón funciona
        binding.btnRegresar?.setOnClickListener {
            finish()
        }
    }
}