package com.zealicon_2024

import EventCard
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.zealicon_2024.R
import com.zealicon_2024.adapters.EventsAdapter
import com.zealicon_2024.databinding.ActivityMainBinding
import java.util.Timer
import java.util.TimerTask
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var eventsAdapter: EventsAdapter
    private lateinit var adapter: CardAdapter
    private var eventsList: ArrayList<EventCard> = ArrayList()
    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val futureDate = Calendar.getInstance()
        futureDate.set(2024, Calendar.MAY, 20, 0, 0)

        val currentDate = Calendar.getInstance()
        Log.d("KING", currentDate.time.toString())
        val timeDifference = futureDate.timeInMillis - currentDate.timeInMillis

        countDownTimer = object : CountDownTimer(timeDifference, 1000) {
            override fun onTick(p0: Long) {
                val remainingTime = calculateTime(p0)
                val array = remainingTime.toCharArray()
                binding.d1TV.text = array[0].toString()
                binding.d2TV.text = array[1].toString()
                binding.h1TV.text = array[3].toString()
                binding.h2TV.text = array[4].toString()
            }

            override fun onFinish() {
            }
        }

        countDownTimer.start()

        binding.buyZeal.setOnClickListener {
            startActivity(Intent(this, ZealTicketActivity::class.java))
        }

        binding.menuButton.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        val events: List<Card> = listOf(
            Card("Cultural Events", R.drawable.cultural),
            Card("Technical Events", R.drawable.technical)
        )
        adapter = CardAdapter(events)
        binding.rv.adapter = adapter
        adapter.onItemClick = {
            if(it.equals("Cultural Events")){
                startActivity(Intent(this, CulturalEvents::class.java))
            }else{
                startActivity(Intent(this, TechnicalEvent::class.java))
            }
        }

        binding.search.setOnClickListener {
            startActivity(Intent(this, SearchViewActivity::class.java))
        }


        for (i in 1..10)

            eventsList.add(
                EventCard(
                    R.drawable.event_image.toString(), 1, "LineUp"
                )
            )

        eventsAdapter = EventsAdapter(eventsList)

        binding.rvEvents.adapter = eventsAdapter

        eventsAdapter.onItemClick = {
            startActivity(Intent(this, EventDetailsActivity::class.java))
        }


        animation()

    }

    fun animation() {
        binding.rv.adapter = adapter
        val NUM_PAGES = 2
        var currentPage = 0

        val timer = Timer()
        val update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            binding.rv.setCurrentItem(currentPage++, true) // Smoothly scroll to the next page
        }
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread(update)
            }
        }, 0, 3000)
    }


    private fun calculateTime(millisUntilFinished: Long): String {
        val days = millisUntilFinished / (1000 * 60 * 60 * 24)
        val hours = millisUntilFinished % (1000 * 60 * 60 * 24) / (1000 * 60 * 60)

        return String.format("%02d:%02d", days, hours)
    }
}