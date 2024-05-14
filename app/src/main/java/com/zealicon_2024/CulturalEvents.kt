package com.zealicon_2024

import EventCard
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.google.firebase.firestore.firestore
import com.zealicon_2024.adapters.EventsAdapter
import com.zealicon_2024.databinding.ActivityCulturalEventsBinding
import com.zealicon_2024.utils.Constants.TAG
import kotlin.math.log

class CulturalEvents : AppCompatActivity() {
    private lateinit var binding: ActivityCulturalEventsBinding
    private lateinit var cardsRV: RecyclerView
    private lateinit var eventsAdapter: EventsAdapter
    private var eventsList: ArrayList<EventCard> = ArrayList() // Initialize eventsList
    private val db = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCulturalEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardsRV = findViewById(R.id.dayWiseRv)
        cardsRV.layoutManager = GridLayoutManager(this , 2)
        // Initialize eventsList before adding items to it
        eventsList = ArrayList()

        eventsAdapter = EventsAdapter(eventsList)

        cardsRV.adapter = eventsAdapter

        binding.back.setOnClickListener{
            finish()
        }

        val eventRef = db.child("culturalEvents").child("data")
        eventRef.addValueEventListener(object: ValueEventListener{
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
            Log.d(TAG, "onCreate: $it")

            val intent = Intent(this, EventDetailsActivity::class.java)
            intent.putExtra("position", it)
            intent.putExtra("path", "culturalEvents")
            startActivity(intent)

        }



    }

}