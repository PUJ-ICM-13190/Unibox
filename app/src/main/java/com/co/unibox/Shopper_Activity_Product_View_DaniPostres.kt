package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ShopperActivityProductViewSellerDaniPostresBinding

class Shopper_Activity_Product_View_DaniPostres : AppCompatActivity() {

    private lateinit var binding: ShopperActivityProductViewSellerDaniPostresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityProductViewSellerDaniPostresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, Shopper_Activity_Home::class.java)
            startActivity(intent)
        }

        binding.btnPago.setOnClickListener {
            val intent = Intent(this, Shopper_Activity_Pay::class.java)
            startActivity(intent)
        }
    }
}
