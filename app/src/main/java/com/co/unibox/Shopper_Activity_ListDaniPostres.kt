package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ShopperActivitySellerDanipostresBinding

class Shopper_Activity_ListDaniPostres : AppCompatActivity() {

    private lateinit var binding: ShopperActivitySellerDanipostresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivitySellerDanipostresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMaracuya.setOnClickListener {
            val intent = Intent(this, Shopper_Activity_Product_View_DaniPostres::class.java)
            startActivity(intent)
        }
    }
}
