package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorUpdatedataBinding
import com.co.unibox.databinding.CompradorUserprofileBinding

class UpdateUserProfileActivity : AppCompatActivity() {

    private lateinit var binding: CompradorUpdatedataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorUpdatedataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegresar.setOnClickListener {
            finish()
        }

    }
}