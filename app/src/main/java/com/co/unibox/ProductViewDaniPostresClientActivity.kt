package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorProductViewDaniPostresBinding

class ProductViewDaniPostresClientActivity : AppCompatActivity() {

    private lateinit var binding: CompradorProductViewDaniPostresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorProductViewDaniPostresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHome.setOnClickListener{
            val intent = Intent(this, HomeClientActivity::class.java)
            startActivity(intent)
        }

        binding.btnPago.setOnClickListener{
            val intent = Intent(this, PayClientActivity::class.java)
            startActivity(intent)
        }
    }
}