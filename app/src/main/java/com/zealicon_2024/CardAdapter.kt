package com.zealicon_2024

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zealicon_2024.databinding.CategoryCardItemBinding

class CardAdapter(private val cardList: List<Card>) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    inner class CardViewHolder(val binding: CategoryCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CategoryCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.bg.setImageResource(cardList[position].img)
        holder.binding.event.text = cardList[position].event
        holder.itemView.setOnClickListener{
            onItemClick?.invoke(cardList[position].event)
        }
        // Bind data to views here

    }

    override fun getItemCount(): Int {
        return cardList.size
    }
}