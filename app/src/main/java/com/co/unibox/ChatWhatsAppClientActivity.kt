package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorChatwhatsappBinding
import com.co.unibox.databinding.CompradorDaniepostresBinding

class ChatWhatsAppClientActivity : AppCompatActivity() {

    private lateinit var binding: CompradorChatwhatsappBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorChatwhatsappBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}