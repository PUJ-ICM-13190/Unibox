package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorUserprofileBinding

class UserProfileClient: AppCompatActivity() {

    private lateinit var binding: CompradorUserprofileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorUserprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegresar.setOnClickListener {
            finish()
        }

        binding.btnMiCuenta.setOnClickListener {
            val intent = Intent(this, UpdateUserProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnModoVendedor.setOnClickListener {
            val intent = Intent(this, HomeSellerActivity::class.java)
            startActivity(intent)
        }

    }
}