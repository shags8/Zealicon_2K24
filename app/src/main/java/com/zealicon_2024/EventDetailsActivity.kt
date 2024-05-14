package com.zealicon_2024

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zealicon_2024.databinding.ActivityEventDetailsBinding
import com.zealicon_2024.utils.Constants.TAG

class EventDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailsBinding
    private val db = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        val pos = intent.getIntExtra("position", 1)
        Log.d(TAG, "onCreate * : $pos")

        val path = intent.getStringExtra("path")


        val eventRef = db.child("$path").child("data").child("$pos")
        eventRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(it: DataSnapshot) {

                val name = it.child("name").getValue(String::class.java).toString()
                val desc = it.child("desc").getValue(String::class.java).toString()
                val date = it.child("date").getValue(String::class.java).toString()
                val time = it.child("time").getValue(String::class.java).toString()
                val image = it.child("image").getValue(String::class.java).toString()
                val phone = it.child("phone").getValue(Long::class.java)!!.toLong()
                val venue = it.child("venue").getValue(String::class.java).toString()
                val prize = it.child("prize").getValue(String::class.java).toString()
                val bannerImage = it.child("bannerPhoto").getValue(String::class.java).toString()

                binding.eventName.text = name
                binding.prize.text = prize
                binding.date.text = date
                binding.desc.text = desc
                binding.time.text = time
                binding.phone.text = phone.toString()
                binding.venue.text = venue
                Glide.with(this@EventDetailsActivity).load(bannerImage).into(binding.eventImage)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}