package com.zealicon_2024

import EventCard
import android.R.bool
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zealicon_2024.adapters.EventsAdapter
import com.zealicon_2024.api.SignupAPI
import com.zealicon_2024.databinding.ActivityMainBinding
import com.zealicon_2024.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.log


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var eventsAdapter: EventsAdapter
    private lateinit var adapter: CardAdapter
    private var eventsList: ArrayList<EventCard> = ArrayList()
    private lateinit var countDownTimer: CountDownTimer
    private val db = FirebaseDatabase.getInstance().reference

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var signupAPI: SignupAPI
    var isZeal = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = tokenManager.getToken().toString()

        if (tokenManager.getZeal() != null && tokenManager.getZeal() != "") {
            isZeal = true
            binding.head.isVisible = false
            binding.desc.isVisible = false
            binding.buyZealButton.isVisible = false
            binding.zealAvailText.isVisible = true
            binding.showZealButton.isVisible = true
        } else {
            binding.progressBar.isVisible = true
            binding.mainLayout.isVisible = false
            binding.transparentBg.isVisible = true
            CoroutineScope(Dispatchers.Main).launch {
                val response = signupAPI.getZealId(token)
                Log.d("KING689", response.body().toString())
                if (response.body() != null && response.body()?.success!!) {
                    isZeal = true
                    binding.head.isVisible = false
                    binding.desc.isVisible = false
                    binding.buyZealButton.isVisible = false
                    binding.zealAvailText.isVisible = true
                    binding.showZealButton.isVisible = true
                    tokenManager.saveZeal(response.body()!!.zeal_id)
                    tokenManager.saveName(response.body()!!.userData.name)
                    tokenManager.saveID(response.body()!!.userData.secure_url)
                    val name = tokenManager.getName()
                    binding.greet.text = "Hello, $name"
                    binding.mainLayout.isVisible = true
                    binding.transparentBg.isVisible = false
                    binding.progressBar.isVisible = false
                }
                else{
                    binding.head.isVisible = true
                    binding.desc.isVisible = true
                    binding.buyZealButton.isVisible = true
                    binding.zealAvailText.isVisible = false
                    binding.showZealButton.isVisible = false
                    binding.mainLayout.isVisible = true
                    binding.transparentBg.isVisible = false
                    binding.progressBar.isVisible = false

                }
            }
        }
        val zeal = tokenManager.getZeal()
        val name = tokenManager.getName()
        val image = tokenManager.getID()

        binding.greet.text = "Hello, $name"
        Log.d("KING1234", "$zeal , $name, $image")
        binding.buyZeal.setOnClickListener {
            if (!isZeal) {
                val purchaseDialogPopup = PurchaseDialogFragment()
                purchaseDialogPopup.show(supportFragmentManager, "BSDialogFragment")
            } else {
                startActivity(Intent(this, ZealTicketActivity::class.java))
            }
        }
        if(name.isNullOrEmpty()){
            binding.greet.text = "Hello,There"
        }


        startTimer()

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
            if (it.equals("Cultural Events")) {
                startActivity(Intent(this, CulturalEvents::class.java))
            } else {
                startActivity(Intent(this, TechnicalEvent::class.java))
            }
        }

        eventsAdapter = EventsAdapter(eventsList)

        binding.rvEvents.adapter = eventsAdapter

        val eventRef = db.child("mainEvents").child("data")
        eventRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val name = it.child("name").getValue(String::class.java).toString()
                    val desc = it.child("desc").getValue(String::class.java).toString()
                    val date = it.child("date").getValue(String::class.java).toString()
                    val time = it.child("time").getValue(String::class.java).toString()
                    val image = it.child("image").getValue(String::class.java).toString()
                    val phone = it.child("phone").getValue(Long::class.java)!!.toLong()
                    val venue = it.child("venue").getValue(String::class.java).toString()
                    val prize = it.child("prize").getValue(String::class.java).toString()

                    val eventData = EventCard(image, date, name, venue, desc, time, prize, phone)

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
            intent.putExtra("path", "mainEvents")
            intent.putExtra("position", it)
            startActivity(intent)
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

    private fun startTimer() {
        val futureDate = Calendar.getInstance()
        futureDate.set(2024, Calendar.MAY, 20, 0, 0)

        val currentDate = Calendar.getInstance()
        Log.d("KING", currentDate.time.toString())
        val timeDifference = futureDate.timeInMillis - currentDate.timeInMillis
        val remainingDays = TimeUnit.MILLISECONDS.toDays(timeDifference)
        val remainingHours = TimeUnit.MILLISECONDS.toHours(timeDifference) % 24
        val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference) % 60
        val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference) % 60

        Log.d("1234", "startTimer: $remainingDays")

        when {
            remainingDays > 0 -> {
                binding.firstTime.text = "DAYS"
                binding.secondTime.text = "HOURS"
            }

            remainingHours > 0 -> {
                binding.firstTime.text = "HOURS"
                binding.secondTime.text = "MINUTES"
            }

            else -> {
                binding.firstTime.text = "MINUTES"
                binding.secondTime.text = "SECONDS"
            }
        }

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

    }


    private fun calculateTime(millisUntilFinished: Long): String {
        val remainingDays = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
        val remainingHours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished) % 24
        val remainingMinutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60
        val remainingSeconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60

        val remainingTime = when {
            remainingDays > 0 -> {
                String.format("%02d:%02d", remainingDays, remainingHours)
            }

            remainingHours > 0 -> {
                String.format("%02d:%02d", remainingHours, remainingMinutes)
            }

            else -> {
                String.format("%02d:%02d", remainingMinutes, remainingSeconds)
            }
        }


        return remainingTime
    }
}