package com.co.unibox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.databinding.ShopperActivityTrustsBoxesBinding

class Activity_Trusts_Boxes : AppCompatActivity(){

    private lateinit var binding: ShopperActivityTrustsBoxesBinding
    private lateinit var adapter: Activity_Trusts_Boxes_Adapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShopperActivityTrustsBoxesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewBoxes
        recyclerView.layoutManager = LinearLayoutManager(this)

        val boxes = ArrayList<Activity_Boxes_Domain>()

        boxes.add(
            Activity_Boxes_Domain(
                "Caja #1",
                "Ingenieria",
                "like"
            )
        )
        boxes.add(
            Activity_Boxes_Domain(
                "Caja #2",
                "Cubos",
                "like"
            )
        )
        boxes.add(
            Activity_Boxes_Domain(
                "Caja #3",
                "Barón",
                "like"
            )
        )
        boxes.add(
            Activity_Boxes_Domain(
                "Caja #4",
                "Basicas",
                "like"
            )
        )
        boxes.add(
            Activity_Boxes_Domain(
                "Caja #5",
                "Artes",
                "like"
            )
        )
        boxes.add(Activity_Boxes_Domain("Caja #6", "67", "like"))
    }

}