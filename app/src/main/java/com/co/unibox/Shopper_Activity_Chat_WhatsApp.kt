package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ShopperActivityChatWhatsappBinding

class Shopper_Activity_Chat_WhatsApp : AppCompatActivity() {

    private lateinit var binding: ShopperActivityChatWhatsappBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityChatWhatsappBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
