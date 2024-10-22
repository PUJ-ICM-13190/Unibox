package com.co.unibox

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Shopper_Activity_Carrusel_Adapter (private val categorias: List<Activity_Category_Domain>) : RecyclerView.Adapter<Shopper_Activity_Carrusel_Adapter.CategoriaViewHolder>() {

    class CategoriaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView: TextView = view.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrusel_category, parent, false)
        return CategoriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriaViewHolder, position: Int) {
        val categoria = categorias[position]
        holder.imageView.setImageResource(categoria.imageRes)
        holder.textView.text = categoria.name

        // Solo la categoría "Mapa" tiene acción de clic
        if (categoria.name == "Mapa") {
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, Shopper_Activity_Map::class.java)
                holder.itemView.context.startActivity(intent)
            }
        }
    }
    override fun getItemCount(): Int {
        return categorias.size
    }
}