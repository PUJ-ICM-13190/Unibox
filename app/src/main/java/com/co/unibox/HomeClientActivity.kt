package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.co.unibox.databinding.ActivityMainBinding
import com.co.unibox.databinding.HomeBinding
import com.co.unibox.databinding.RegisterBinding

class HomeClientActivity: AppCompatActivity() {

    private lateinit var binding: HomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnMapa.setOnClickListener{
            val intent = Intent(this, MapClientActivity::class.java)
            startActivity(intent)
        }

    }
}