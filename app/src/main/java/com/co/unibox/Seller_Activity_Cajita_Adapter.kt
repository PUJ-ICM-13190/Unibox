package com.co.unibox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.co.unibox.data.Cajita

class Seller_Activity_Cajita_Adapter(
    private val cajitas: MutableList<Cajita>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<Seller_Activity_Cajita_Adapter.CajitaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CajitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activity_cajita, parent, false)
        return CajitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CajitaViewHolder, position: Int) {
        val cajita = cajitas[position]
        holder.bind(cajita)
        holder.itemView.setOnClickListener { onItemClick(position) }
    }

    override fun getItemCount(): Int = cajitas.size

    class CajitaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombreTextView: TextView = itemView.findViewById(R.id.nombreCajita)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.descripcionCajita)

        fun bind(cajita: Cajita) {
            nombreTextView.text = cajita.nombre
            descripcionTextView.text = cajita.descripcion
        }
    }
}
