package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorDaniepostresBinding

class ListDaniPostresClientActivity : AppCompatActivity() {

    private lateinit var binding: CompradorDaniepostresBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorDaniepostresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMaracuya.setOnClickListener{
            val intent = Intent(this, ProductViewDaniPostresClientActivity::class.java)
            startActivity(intent)
        }
    }
}