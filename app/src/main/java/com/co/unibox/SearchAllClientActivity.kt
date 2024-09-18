package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.co.unibox.databinding.CompradorDaniepostresBinding
import com.co.unibox.databinding.CompradorViewallproductswithsearchBinding

class SearchAllClientActivity : AppCompatActivity(){

    private lateinit var binding: CompradorViewallproductswithsearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorViewallproductswithsearchBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buscarporb.setOnClickListener{
            val intent = Intent(this, SearchBClientActivity::class.java)
            startActivity(intent)
        }

        binding.verbombombum.setOnClickListener{
            val intent = Intent(this, ViewGeneralClientActivity::class.java)
            startActivity(intent)
        }


        binding.btnRegresar?.setOnClickListener {
            finish()
        }
    }
}