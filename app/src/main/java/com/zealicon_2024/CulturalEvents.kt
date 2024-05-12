package com.zealicon_2024

import EventCard
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zealicon_2024.adapters.EventsAdapter
import com.zealicon_2024.databinding.ActivityCulturalEventsBinding

class CulturalEvents : AppCompatActivity() {
    private lateinit var binding: ActivityCulturalEventsBinding
    private lateinit var cardsRV: RecyclerView
    private lateinit var eventsAdapter: EventsAdapter
    private var eventsList: ArrayList<EventCard> = ArrayList() // Initialize eventsList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCulturalEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardsRV = findViewById(R.id.dayWiseRv)
        cardsRV.layoutManager = GridLayoutManager(this , 2)
        // Initialize eventsList before adding items to it
        eventsList = ArrayList()
        for (i in 1..10 )
            eventsList.add(
                EventCard(
                    R.drawable.event_image.toString(), 1, "LineUp"
                )
            )
        eventsAdapter = EventsAdapter(eventsList)
        cardsRV.adapter = eventsAdapter
        eventsAdapter.onItemClick = {
            startActivity(Intent(this, EventDetailsActivity::class.java))
        }
        eventsAdapter.notifyDataSetChanged()

        binding.back.setOnClickListener{
            finish()
        }
    }

}