package com.co.unibox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ProductsAdapter(private val products: ArrayList<ProductDomain>) :
    RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_products, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.nameProduct.text = product.getNameProduct()
        holder.categoriaProduct.text = product.getCategoriaProduct()
        holder.price.text = product.getPrice()

        val drawableResourceId = holder.itemView.resources
            .getIdentifier(product.getPic(), "drawable", holder.itemView.context.packageName)


        Glide.with(context)
            .load(drawableResourceId)
            .into(holder.pic)


        // You can remove this switch if you want all items to have the same background
        // or modify it to apply different backgrounds based on your needs
        holder.price.setBackgroundResource(R.drawable.rounded_price_background)
    }

    override fun getItemCount(): Int = products.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameProduct: TextView = itemView.findViewById(R.id.nameProduct)
        val categoriaProduct: TextView = itemView.findViewById(R.id.categoriaProducto)
        val price: TextView = itemView.findViewById(R.id.product_price)
        val pic: ImageView = itemView.findViewById(R.id.imageProduct)
    }
}