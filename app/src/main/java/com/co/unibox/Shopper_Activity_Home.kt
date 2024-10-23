package com.co.unibox

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.databinding.ShopperActivityHomeBinding

class Shopper_Activity_Home : AppCompatActivity() {
    private lateinit var binding: ShopperActivityHomeBinding
    private lateinit var adapter: Seller_Activity_Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var carruselAdapter: Shopper_Activity_Carrusel_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCarrusel()

        setupRecyclerView()

        binding.userProfile.setOnClickListener {
            val intent = Intent(this, Shopper_Activity_User_Profile::class.java)
            startActivity(intent)
        }

        binding.btnLupa.setOnClickListener {
            val intent = Intent(this, Shopper_Activity_Search_All::class.java)
            startActivity(intent)
        }

    }

    private fun setupCarrusel() {
        val categorias = listOf(
            Activity_Category_Domain(1,"Mapa", R.drawable.mapa),
            Activity_Category_Domain(2, "Cajas de la Confianza", R.drawable.cajaconfianza),
            Activity_Category_Domain(3, "Promociones", R.drawable.promociones)
        )

        carruselAdapter = Shopper_Activity_Carrusel_Adapter(categorias)
        binding.viewPagerCarrusel.adapter = carruselAdapter
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewEmprendimientos
        recyclerView.layoutManager = LinearLayoutManager(this)

        val items = ArrayList<Seller_Activity_Domain>()
        items.add(
            Seller_Activity_Domain(
                "Ingenieria",
                "Caprichos",
                "comidacaprichos",
                "capricho"
            )
        )
        items.add(
            Seller_Activity_Domain(
                "Ingenieria",
                "Caprichos",
                "comidacaprichos",
                "capricho"
            )
        )
        items.add(
            Seller_Activity_Domain(
                "Ingenieria",
                "Caprichos",
                "comidacaprichos",
                "capricho"
            )
        )
        items.add(
            Seller_Activity_Domain(
                "Ingenieria",
                "Caprichos",
                "comidacaprichos",
                "capricho"
            )
        )
        items.add(
            Seller_Activity_Domain(
                "Ingenieria",
                "Caprichos",
                "comidacaprichos",
                "capricho"
            )
        )

        adapter = Seller_Activity_Adapter(items)
        recyclerView.adapter = adapter
    }
}
