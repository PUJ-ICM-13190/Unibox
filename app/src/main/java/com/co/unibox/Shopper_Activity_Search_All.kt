package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.databinding.ShopperActivityViewAllProductsResearchBinding
import androidx.appcompat.widget.SearchView

class Shopper_Activity_Search_All : AppCompatActivity() {

    private lateinit var binding: ShopperActivityViewAllProductsResearchBinding
    private lateinit var adapter: Activity_Products_Adapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityViewAllProductsResearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchView() // Configurar la búsqueda

        // Verifica si el botón funciona
        binding.btnRegresar?.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewProductos
        recyclerView.layoutManager = LinearLayoutManager(this)

        val products = ArrayList<Activity_Product_Domain>()
        products.add(Activity_Product_Domain("Boliquesos", "Paquetes", "boliqueso", "$2.000"))
        products.add(Activity_Product_Domain("Bombon", "Dulces", "bonbon", "$500"))
        products.add(Activity_Product_Domain("Cupcakes", "Postres", "cupcakes", "$3.000"))
        products.add(Activity_Product_Domain("Bowl", "Comida", "bowl", "$1.000"))
        products.add(Activity_Product_Domain("Barrilete", "Dulces", "barrilete", "$1.500"))
        products.add(Activity_Product_Domain("Nucita", "Dulces", "nucita", "$800"))
        products.add(Activity_Product_Domain("Okaloca", "Dulces", "okaloca", "$1.000"))
        products.add(Activity_Product_Domain("Pie de Limón", "Postres", "pielimon", "$3.000"))
        products.add(Activity_Product_Domain("Quipitos", "Dulces", "quipitos", "$800"))
        products.add(Activity_Product_Domain("Revolcon", "Dulces", "revolcon", "$300"))
        products.add(Activity_Product_Domain("Sandwich", "Comida", "sandwich", "$5.000"))

        adapter = Activity_Products_Adapter(products)
        recyclerView.adapter = adapter
    }

    private fun setupSearchView() {
        val searchView = binding.searchview

        // Configurar el listener para el texto de búsqueda
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false // No es necesario manejar la acción de submit
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtrar los productos cuando el texto cambia
                adapter.filter(newText ?: "")
                return true
            }
        })
    }
}