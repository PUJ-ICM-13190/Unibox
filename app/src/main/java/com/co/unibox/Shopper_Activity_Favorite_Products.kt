package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.databinding.ShopperActivityFavoriteProductBinding


class Shopper_Activity_Favorite_Products : AppCompatActivity(){

    private lateinit var binding: ShopperActivityFavoriteProductBinding

    private lateinit var adapter: Shopper_Activity_Favorite_Product
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityFavoriteProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewFavoritos
        recyclerView.layoutManager = LinearLayoutManager(this)

        val products = ArrayList<Shopper_Activity_Favorite_Product_Domain>()
        products.add(
            Shopper_Activity_Favorite_Product_Domain(
                "Boliquesos",
                "Paquetes",
                "boliqueso",
                "$2.000",
                "like_1"
            )
        )
        products.add(
            Shopper_Activity_Favorite_Product_Domain(
                "Okaloca",
                "Dulces",
                "okaloca",
                "$1.000",
                "like_1"
            )
        )
        products.add(
            Shopper_Activity_Favorite_Product_Domain(
                "Pie de Limón",
                "Postres",
                "pielimon",
                "$3.000",
                "like_1"
            )
        )
        products.add(
            Shopper_Activity_Favorite_Product_Domain(
                "Quipitos",
                "Dulces",
                "quipitos",
                "$800",
                "like_1"
            )
        )
        products.add(
            Shopper_Activity_Favorite_Product_Domain(
                "Revolcon",
                "Dulces",
                "revolcon",
                "$300",
                "like_1"
            )
        )
        products.add(
            Shopper_Activity_Favorite_Product_Domain(
                "Sandwich",
                "Comida",
                "sandwich",
                "$5.000",
                "like_1"
            )
        )

        adapter = Shopper_Activity_Favorite_Product(products)
        recyclerView.adapter = adapter
    }
}