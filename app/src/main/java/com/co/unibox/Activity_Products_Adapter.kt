package com.co.unibox

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Activity_Products_Adapter(private var products: ArrayList<Activity_Product_Domain>) :
    RecyclerView.Adapter<Activity_Products_Adapter.ViewHolder>() {

    private var filteredProducts = ArrayList(products)
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity_products, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = filteredProducts[position]
        holder.nameProduct.text = product.getNameProduct()
        holder.categoriaProduct.text = product.getCategoriaProduct()
        holder.price.text = product.getPrice()

        val drawableResourceId = holder.itemView.resources
            .getIdentifier(product.getPic(), "drawable", holder.itemView.context.packageName)

        Glide.with(context)
            .load(drawableResourceId)
            .into(holder.pic)

        holder.price.setBackgroundResource(R.drawable.rounded_price_background)

        // Configurar el clic en el elemento
        holder.itemView.setOnClickListener {
            val intent = Intent(context, Activity_View_Product::class.java).apply {
                putExtra("product_name", product.getNameProduct())
                putExtra("product_category", product.getCategoriaProduct())
                putExtra("product_price", product.getPrice())
                putExtra("product_image", product.getPic())
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = filteredProducts.size

    fun filter(query: String) {
        filteredProducts = if (query.isEmpty()) {
            ArrayList(products)
        } else {
            val filteredList = products.filter {
                it.getNameProduct().toLowerCase().contains(query.toLowerCase())
            }
            ArrayList(filteredList)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameProduct: TextView = itemView.findViewById(R.id.nameProduct)
        val categoriaProduct: TextView = itemView.findViewById(R.id.categoriaProducto)
        val price: TextView = itemView.findViewById(R.id.product_price)
        val pic: ImageView = itemView.findViewById(R.id.imageProduct)
    }
}