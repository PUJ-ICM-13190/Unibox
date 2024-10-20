package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ShopperActivityProductViewGeneralBinding

class Shopper_Activity_View_General : AppCompatActivity() {

    private lateinit var binding: ShopperActivityProductViewGeneralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityProductViewGeneralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegresar.setOnClickListener {
            val intent = Intent(this, Shopper_Activity_Home::class.java)
            startActivity(intent)
        }
    }
}
