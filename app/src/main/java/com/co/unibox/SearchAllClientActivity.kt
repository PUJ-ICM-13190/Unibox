package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.databinding.CompradorViewAllproductswithsearchBinding

class SearchAllClientActivity : AppCompatActivity() {

    private lateinit var binding: CompradorViewAllproductswithsearchBinding
    private lateinit var adapter: ProductsAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CompradorViewAllproductswithsearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewProductos
        recyclerView.layoutManager = LinearLayoutManager(this)

        val products = ArrayList<ProductDomain>()
        products.add(ProductDomain("Galleta", "Dulce", "boliqueso", "$1.00"))

        adapter = ProductsAdapter(products)
        recyclerView.adapter = adapter
    }
}