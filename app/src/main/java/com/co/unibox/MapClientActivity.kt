package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorMapBinding

class MapClientActivity : AppCompatActivity() {
    private lateinit var binding: CompradorMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verifica si el botón funciona
        binding.btnRegresar?.setOnClickListener {
            finish()
        }
    }
}