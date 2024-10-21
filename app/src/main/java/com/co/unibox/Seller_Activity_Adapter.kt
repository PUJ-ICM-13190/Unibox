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

class Seller_Activity_Adapter(private val items: ArrayList<Seller_Activity_Domain>) :
    RecyclerView.Adapter<Seller_Activity_Adapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity_seller, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tittle.text = item.getTittle()
        holder.location.text = item.getLocation()

        val drawableResourceId = holder.itemView.resources
            .getIdentifier(item.getPicPath(), "drawable", holder.itemView.context.packageName)

        val drawableResourceId2 = holder.itemView.resources
            .getIdentifier(item.getLogo(), "drawable", holder.itemView.context.packageName)

        Glide.with(context)
            .load(drawableResourceId)
            .into(holder.pic)

        Glide.with(context)
            .load(drawableResourceId2)
            .into(holder.logo)

        // You can remove this switch if you want all items to have the same background
        // or modify it to apply different backgrounds based on your needs
        holder.layout.setBackgroundResource(R.drawable.list_background1)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tittle: TextView = itemView.findViewById(R.id.tittle)
        val location: TextView = itemView.findViewById(R.id.location)
        val pic: ImageView = itemView.findViewById(R.id.picture)
        val logo: ImageView = itemView.findViewById(R.id.logo)
        val button: Button = itemView.findViewById(R.id.boton)
        val layout: ConstraintLayout = itemView.findViewById(R.id.layout)
    }
}