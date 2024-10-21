package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ShopperActivityProductViewGeneralBinding

class Activity_View_Product : AppCompatActivity() {

    private lateinit var binding: ShopperActivityProductViewGeneralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityProductViewGeneralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Verifica si el botón funciona
        binding.btnRegresar?.setOnClickListener {
            finish()
        }
    }
}
