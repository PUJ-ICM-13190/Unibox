package com.co.unibox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductMapViewAdapter (private val productList: List<Product>) : RecyclerView.Adapter<ProductMapViewAdapter.ProductViewHolder>() {


    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productName: TextView = view.findViewById(R.id.product_name)
        val productDescription: TextView = view.findViewById(R.id.product_description)
        val productPrice: TextView = view.findViewById(R.id.productm_price)
        val productStock: TextView = view.findViewById(R.id.product_stock)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_map_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.name
        holder.productDescription.text = product.description
        holder.productPrice.text = "$${product.price}"
        holder.productStock.text = "Restante: ${product.stock}"
    }

    override fun getItemCount(): Int {
        return productList.size
    }

}