package com.zealicon_2024

import EventCard
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zealicon_2024.adapters.EventsAdapter
import com.zealicon_2024.databinding.ActivityTechnicalEventBinding

class TechnicalEvent : AppCompatActivity() {
    private lateinit var binding: ActivityTechnicalEventBinding
    private lateinit var cardsRV: RecyclerView
    private lateinit var eventsAdapter: EventsAdapter
    private var eventsList: ArrayList<EventCard> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTechnicalEventBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_technical_event)

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


    }

    fun goBack(view: View) {
        finish()
    }
}