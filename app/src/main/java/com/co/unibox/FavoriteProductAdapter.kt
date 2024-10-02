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

class FavoriteProductAdapter(private val products: ArrayList<FavoriteProductDomain>) :
    RecyclerView.Adapter<FavoriteProductAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_product, parent, false)
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

        val drawableResourceId2 = holder.itemView.resources
            .getIdentifier(product.getPicLike(), "drawable", holder.itemView.context.packageName)

        Glide.with(context)
            .load(drawableResourceId)
            .into(holder.pic)

        Glide.with(context)
            .load(drawableResourceId2)
            .into(holder.picLike)

        holder.price.setBackgroundResource(R.drawable.rounded_price_background)
    }

    override fun getItemCount(): Int = products.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameProduct: TextView = itemView.findViewById(R.id.nameProduct)
        val categoriaProduct: TextView = itemView.findViewById(R.id.categoriaProducto)
        val price: TextView = itemView.findViewById(R.id.product_price)
        val pic: ImageView = itemView.findViewById(R.id.imageProduct)
        val picLike: ImageView = itemView.findViewById(R.id.like)
    }
}