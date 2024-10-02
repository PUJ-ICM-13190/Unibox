package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.databinding.CompradorFavoriteProductBinding

class ActivityShopperFavoriteProducts : AppCompatActivity(){

    private lateinit var binding: CompradorFavoriteProductBinding
    private lateinit var adapter: FavoriteProductAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorFavoriteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewFavoritos
        recyclerView.layoutManager = LinearLayoutManager(this)

        val products = ArrayList<FavoriteProductDomain>()
        products.add(FavoriteProductDomain("Boliquesos", "Paquetes", "boliqueso", "$2.000", "like_1"))
        products.add(FavoriteProductDomain("Okaloca", "Dulces", "okaloca", "$1.000", "like_1"))
        products.add(FavoriteProductDomain("Pie de Limón", "Postres", "pielimon", "$3.000", "like_1"))
        products.add(FavoriteProductDomain("Quipitos", "Dulces", "quipitos", "$800", "like_1"))
        products.add(FavoriteProductDomain("Revolcon", "Dulces", "revolcon", "$300", "like_1"))
        products.add(FavoriteProductDomain("Sandwich", "Comida", "sandwich", "$5.000", "like_1"))

        adapter = FavoriteProductAdapter(products)
        recyclerView.adapter = adapter
    }
}