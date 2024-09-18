package com.co.unibox

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

        // Configurar el título de la toolbar
        binding.topAppBar.title = getString(R.string.tus_cajitas)
    }
}