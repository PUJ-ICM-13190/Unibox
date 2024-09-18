package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorWhatsappapiBinding

class WhatsAppClientActivity : AppCompatActivity() {

    private lateinit var binding: CompradorWhatsappapiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorWhatsappapiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHome.setOnClickListener{
            val intent = Intent(this, HomeClientActivity::class.java)
            startActivity(intent)
        }

        binding.btnChat.setOnClickListener{
            val intent = Intent(this, ChatWhatsAppClientActivity::class.java)
            startActivity(intent)
        }
    }
}