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
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zealicon_2024.adapters.EventsAdapter
import com.zealicon_2024.databinding.ActivityTechnicalEventBinding

class TechnicalEvent : AppCompatActivity() {
    private lateinit var binding: ActivityTechnicalEventBinding
    private lateinit var cardsRV: RecyclerView
    private lateinit var eventsAdapter: EventsAdapter
    private var eventsList: ArrayList<EventCard> = ArrayList()
    private val db = FirebaseDatabase.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTechnicalEventBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_technical_event)

        cardsRV = findViewById(R.id.dayWiseRv)
        cardsRV.layoutManager = GridLayoutManager(this , 2)
        // Initialize eventsList before adding items to it
        eventsList = ArrayList()
        eventsAdapter = EventsAdapter(eventsList)
        cardsRV.adapter = eventsAdapter

        val eventRef = db.child("technicalEvents").child("data")
        eventRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val name = it.child("name").getValue(String::class.java).toString()
                    val desc = it.child("desc").getValue(String::class.java).toString()
                    val date = it.child("date").getValue(String::class.java).toString()
                    val time = it.child("time").getValue(String::class.java).toString()
                    val image = it.child("image").getValue(String::class.java).toString()
                    val phone = it.child("phone").getValue(Long::class.java)!!.toLong()
                    val venue = it.child("venue").getValue(String::class.java).toString()
                    val prize = it.child("prize").getValue(String::class.java).toString()

                    val eventData = EventCard(image,date,name,venue,desc,time,prize,phone)

                    eventsList.add(eventData)
                }

                eventsAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        eventsAdapter.onItemClick = {
            val intent = Intent(this, EventDetailsActivity::class.java)
            intent.putExtra("path", "technicalEvents")
            intent.putExtra("position", it)
            startActivity(intent)
        }

    }

    fun goBack(view: View) {
        finish()
    }
}