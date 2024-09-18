package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.ViewSellerMisCajitasBinding

class MisCajitasSellerActivity:AppCompatActivity() {

    private lateinit var binding: ViewSellerMisCajitasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewSellerMisCajitasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        binding.topAppBar.title = getString(R.string.tus_cajitas)

        binding.cardCajita1.setOnClickListener {
            val intent = Intent(this, DetailsCajitaSeller::class.java)
            startActivity(intent)
        }
    }
}