package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorPayBinding

class PayClientActivity: AppCompatActivity() {

    private lateinit var binding: CompradorPayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHome.setOnClickListener{
            val intent = Intent(this, HomeClientActivity::class.java)
            startActivity(intent)
        }

        binding.btnWhat.setOnClickListener{
            val intent = Intent(this, WhatsAppClientActivity::class.java)
            startActivity(intent)
        }
    }
}