package com.co.unibox

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.adaptador.ProductAdapter
import com.co.unibox.data.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

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

        // Inicializar el adaptador
        adapter = ProductAdapter(productList) { product ->
            val intent = Intent(this, Seller_Activity_Edit_Product::class.java)
            intent.putExtra("productId", product.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        configureAddButton()
        loadProductsInRealTime() // Escucha de datos en tiempo real
        configureBackButton()

    }

    // Configurar el botón de regresar
    private fun configureBackButton() {
        val btnVolver = findViewById<ImageButton>(R.id.btn_back)
        btnVolver.setOnClickListener { finish() }
    }

    private fun configureAddButton() {
        val btnAgregar = findViewById<ImageButton>(R.id.btn_add)
        if (btnAgregar == null) {
            Log.e("Seller_Activity", "Add button not found in layout")
            return
        }
        btnAgregar.setOnClickListener {
            val intent = Intent(this, Seller_Activity_Add_Product::class.java)
            startActivity(intent)
        }
    }

    private fun loadProductsInRealTime() {
        val userId = auth.currentUser?.uid ?: return

        database.child("products").child(userId).addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val product = snapshot.getValue(Product::class.java)
                if (product != null) {
                    productList.add(product)
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val updatedProduct = snapshot.getValue(Product::class.java)
                val index = productList.indexOfFirst { it.id == updatedProduct?.id }
                if (index != -1) {
                    productList[index] = updatedProduct!!
                    adapter.notifyItemChanged(index)
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                val removedProduct = snapshot.getValue(Product::class.java)
                productList.removeAll { it.id == removedProduct?.id }
                adapter.notifyDataSetChanged()
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {
                Log.e("Seller_Activity", "Error en la escucha de productos: ${error.message}")
                Toast.makeText(this@Seller_Activity_List_Products, "Error al cargar los productos.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
