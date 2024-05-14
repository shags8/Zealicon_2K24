package com.zealicon_2024.adapters

import EventCard
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zealicon_2024.R
import com.zealicon_2024.models.ContactTeam

class ContactUsAdapter(
    private val list: List<ContactTeam>
) : RecyclerView.Adapter<ContactUsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.eventImage)
        val name: TextView = itemView.findViewById(R.id.post)
        val number: TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.team_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardList = list[position]
        holder.image.setImageResource(cardList.photo)
        holder.number.text=cardList.number
        holder.name.text=cardList.name

    }
}