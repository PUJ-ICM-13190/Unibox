package com.co.unibox

import android.content.Intent
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

        // Verifica si el botón funciona
        binding.btnRegresar?.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewProductos
        recyclerView.layoutManager = LinearLayoutManager(this)

        val products = ArrayList<ProductDomain>()
        products.add(ProductDomain("Boliquesos", "Paquetes", "boliqueso", "$2.000"))
        products.add(ProductDomain("Bombon", "Dulces", "bonbon", "$500"))
        products.add(ProductDomain("Cupcakes", "Postres", "cupcakes", "$3.000"))
        products.add(ProductDomain("Bowl", "Comida", "bowl", "$1.000"))
        products.add(ProductDomain("Barrilete", "Dulces", "barrilete", "$1.500"))
        products.add(ProductDomain("Nucita", "Dulces", "nucita", "$800"))
        products.add(ProductDomain("Okaloca", "Dulces", "okaloca", "$1.000"))
        products.add(ProductDomain("Pie de Limón", "Postres", "pielimon", "$3.000"))
        products.add(ProductDomain("Quipitos", "Dulces", "quipitos", "$800"))
        products.add(ProductDomain("Revolcon", "Dulces", "revolcon", "$300"))
        products.add(ProductDomain("Sandwich", "Comida", "sandwich", "$5.000"))

        adapter = ProductsAdapter(products)
        recyclerView.adapter = adapter
    }
}