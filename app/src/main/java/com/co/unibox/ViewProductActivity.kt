package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorProductViewGeneralBinding

class ViewProductActivity : AppCompatActivity(){

    private lateinit var binding: CompradorProductViewGeneralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorProductViewGeneralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verifica si el botón funciona
        binding.btnRegresar?.setOnClickListener {
            finish()
        }
    }
}