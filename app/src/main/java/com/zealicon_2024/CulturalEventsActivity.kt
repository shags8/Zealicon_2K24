package com.zealicon_2024

import EventCard
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zealicon_2024.adapters.EventsAdapter
import com.zealicon_2024.databinding.ActivityCulturalEventsBinding
import com.zealicon_2024.databinding.FragmentLoginBinding

class CulturalEventsActivity : AppCompatActivity() {
    private lateinit var cardsRV: RecyclerView
    private lateinit var eventsAdapter: EventsAdapter
    private var eventsList: ArrayList<EventCard> = ArrayList() // Initialize eventsList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cultural_events)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        cardsRV = findViewById(R.id.dayWiseRv)
        cardsRV.layoutManager = GridLayoutManager(this , 2)
        eventsList = ArrayList()
        for (i in 1..10 )
        eventsList.add(
            EventCard(
                R.drawable.event_image.toString(), 1, "LineUp"
            )
        )
        eventsAdapter = EventsAdapter(eventsList)
        cardsRV.adapter = eventsAdapter
        eventsAdapter.notifyDataSetChanged()
    }
}