package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ActivityShopperMapBinding

class MapClientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopperMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopperMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verifica si el botón funciona
        binding.btnRegresar?.setOnClickListener {
            finish()
        }
    }
}