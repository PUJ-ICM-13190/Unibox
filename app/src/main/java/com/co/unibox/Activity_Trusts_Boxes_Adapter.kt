package com.co.unibox

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Activity_Trusts_Boxes_Adapter(private val boxes: ArrayList<Activity_Boxes_Domain>) :
    RecyclerView.Adapter<Activity_Trusts_Boxes_Adapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity_list_boxes, parent, false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val box = boxes[position]
        holder.nameBox.text = box.getNameBox()
        holder.location.text = box.getLocation()

        val drawableResourceId = holder.itemView.resources
            .getIdentifier(box.getLike(), "drawable", holder.itemView.context.packageName)


        Glide.with(context)
            .load(drawableResourceId)
            .into(holder.pic)
    }

    override fun getItemCount(): Int = boxes.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameBox: TextView = itemView.findViewById(R.id.nameBox)
        val location: TextView = itemView.findViewById(R.id.location)
        val pic: ImageView = itemView.findViewById(R.id.like)
    }

}