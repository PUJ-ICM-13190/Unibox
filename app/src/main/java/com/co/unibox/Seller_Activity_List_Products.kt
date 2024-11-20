package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.helper.widget.MotionEffect.TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.adaptador.ProductAdapter
import com.co.unibox.data.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Seller_Activity_List_Products : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private val productList = mutableListOf<Product>()
    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seller_activity_list_products)

        recyclerView = findViewById(R.id.recyclerView_products)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ProductAdapter(productList) { product ->
            val intent = Intent(this, Seller_Activity_Edit_Product::class.java)
            intent.putExtra("productId", product.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        loadProducts()
        configureAddButton()
    }

    private fun configureAddButton() {
        val btnAgregar = findViewById<ImageButton>(R.id.btn_add)
        if (btnAgregar == null) {
            Log.e(TAG, "Add button not found in layout")
            return
        }
        btnAgregar.setOnClickListener {
            Log.d(TAG, "Add button clicked")
            val intent = Intent(this, Seller_Activity_Add_Product::class.java)
            Log.d(TAG, "Navigating to AddProductSellerActivity")
            startActivity(intent)
        }
    }



    private fun loadProducts() {
        val userId = auth.currentUser?.uid ?: return
        database.child("products").child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    productList.clear()
                    for (productSnapshot in snapshot.children) {
                        val product = productSnapshot.getValue(Product::class.java)
                        if (product != null) productList.add(product)
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@Seller_Activity_List_Products, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
