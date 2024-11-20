package com.co.unibox.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.co.unibox.R
import com.co.unibox.data.Product

class ProductAdapter(
    private val productList: List<Product>,
    private val onEditClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.product_image)
        val productName: TextView = itemView.findViewById(R.id.product_name)
        val productCategory: TextView = itemView.findViewById(R.id.product_category)
        val productPrice: TextView = itemView.findViewById(R.id.product_price)
        val btnEdit: ImageButton = itemView.findViewById(R.id.btn_edit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.productName.text = product.name ?: "Sin nombre"
        holder.productCategory.text = product.category ?: "Sin categoría"
        holder.productPrice.text = "$${product.price ?: 0.0}"
        Glide.with(holder.productImage.context)
            .load(product.imageUrl )
            .placeholder(R.drawable.ic_products)
            .error(R.drawable.bonbon) // Imagen en caso de error
            .into(holder.productImage)

        holder.btnEdit.setOnClickListener { onEditClick(product) }
    }


    override fun getItemCount(): Int = productList.size
}