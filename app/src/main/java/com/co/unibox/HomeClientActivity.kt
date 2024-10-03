package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.databinding.ActivityShopperHomeBinding


class HomeClientActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopperHomeBinding
    private lateinit var adapter: EmprendimientosAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopperHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.userProfile.setOnClickListener {
            val intent = Intent(this, UserProfileClient::class.java)
            startActivity(intent)
        }


        binding.btnMapa.setOnClickListener {
            val intent = Intent(this, MapClientActivity::class.java)
            startActivity(intent)
        }

        binding.btnDaniPostres.setOnClickListener {
            val intent = Intent(this, ListDaniPostresClientActivity::class.java)
            startActivity(intent)
        }

        binding.btnLupa.setOnClickListener{
            val intent = Intent(this, SearchAllClientActivity::class.java)
            startActivity(intent)
        }

    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewEmprendimientos
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = ArrayList<EmprendimientosDomain>()
        items.add(EmprendimientosDomain("Ingenieria", "Caprichos", "comidacaprichos", "capricho"))
        items.add(EmprendimientosDomain("Ingenieria", "Caprichos", "comidacaprichos", "capricho"))
        items.add(EmprendimientosDomain("Ingenieria", "Caprichos", "comidacaprichos", "capricho"))
        items.add(EmprendimientosDomain("Ingenieria", "Caprichos", "comidacaprichos", "capricho"))
        items.add(EmprendimientosDomain("Ingenieria", "Caprichos", "comidacaprichos", "capricho"))


        adapter = EmprendimientosAdapter(items)
        recyclerView.adapter = adapter
    }
}