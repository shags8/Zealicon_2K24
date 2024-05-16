package com.zealicon_2024.adapters

import EventCard
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zealicon_2024.R

class EventsAdapter(
    private val list: List<EventCard>
) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {


    var onItemClick: ((Int) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.eventImage)
        val day: TextView = itemView.findViewById(R.id.eventDateCategory)
        val event: TextView = itemView.findViewById(R.id.eventTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.event_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardList = list[position]
        holder.day.text = cardList.date
        Glide.with(holder.itemView.context).load(cardList.image).into(holder.image)
        holder.image.setImageResource(R.drawable.event_image)
        holder.event.text= cardList.name
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(position)
        }
    }
}