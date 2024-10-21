package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ShopperActivityWhatsappApiBinding

class Shopper_Activity_WhatsApp : AppCompatActivity() {

    private lateinit var binding: ShopperActivityWhatsappApiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityWhatsappApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnHome.setOnClickListener{
            val intent = Intent(this, Shopper_Activity_Home::class.java)
            startActivity(intent)
        }

        binding.btnChat.setOnClickListener{
            val intent = Intent(this, Shopper_Activity_Chat_WhatsApp::class.java)
            startActivity(intent)
        }
    }
}

