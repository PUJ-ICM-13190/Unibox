package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.databinding.ActivityShopperTrustsBoxesBinding

class ActivityTrustsBoxes : AppCompatActivity(){

    private lateinit var binding: ActivityShopperTrustsBoxesBinding
    private lateinit var adapter: TrustsBoxesAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopperTrustsBoxesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewBoxes
        recyclerView.layoutManager = LinearLayoutManager(this)

        val boxes = ArrayList<BoxesDomain>()

        boxes.add(BoxesDomain("Caja #1", "Ingenieria", "like"))
        boxes.add(BoxesDomain("Caja #2", "Cubos", "like"))
        boxes.add(BoxesDomain("Caja #3", "Barón", "like"))
        boxes.add(BoxesDomain("Caja #4", "Basicas", "like"))
        boxes.add(BoxesDomain("Caja #5", "Artes", "like"))
        boxes.add(BoxesDomain("Caja #6", "67", "like"))
    }

}