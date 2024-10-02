package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorProductViewGeneralBinding

class ViewGeneralClientActivity  : AppCompatActivity() {

    private lateinit var binding: CompradorProductViewGeneralBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorProductViewGeneralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegresar.setOnClickListener{
            val intent = Intent(this, HomeClientActivity::class.java)
            startActivity(intent)
        }

    }
}