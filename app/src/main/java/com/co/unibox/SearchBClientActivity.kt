package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorDaniepostresBinding
import com.co.unibox.databinding.CompradorViewallproductswithsearchBoliquesoBinding

class SearchBClientActivity : AppCompatActivity() {
    private lateinit var binding: CompradorViewallproductswithsearchBoliquesoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorViewallproductswithsearchBoliquesoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegresar?.setOnClickListener {
            finish()
        }

    }
}