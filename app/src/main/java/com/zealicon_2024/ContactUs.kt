package com.zealicon_2024

import EventCard
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zealicon_2024.adapters.ContactUsAdapter
import com.zealicon_2024.adapters.EventsAdapter
import com.zealicon_2024.databinding.ActivityContactUsBinding
import com.zealicon_2024.databinding.ActivityCulturalEventsBinding
import com.zealicon_2024.databinding.ActivityMainBinding
import com.zealicon_2024.databinding.FragmentPurchaseDialogBinding
import com.zealicon_2024.models.ContactTeam

class ContactUs : AppCompatActivity() {
    private lateinit var cardsRV: RecyclerView
    private lateinit var contactUsAdapter: ContactUsAdapter
    private var teamList: ArrayList<ContactTeam> = ArrayList()
    private lateinit var binding: ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardsRV = findViewById(R.id.contact_people)
        cardsRV.layoutManager = GridLayoutManager(this, 2)

        teamList = ArrayList()


        teamList.addAll(
            listOf(
                ContactTeam(R.drawable.prakhar_sharma, "Prakhar Sharma", "9140884038"),
                ContactTeam(R.drawable.anant_mishra, "Anant Mishra", "7393869008"),
                ContactTeam(R.drawable.ayush_pandey, "Ayush Pandey", "9569452271"),
                ContactTeam(R.drawable.krishna_prakhar, "Krishna Prakhar", "7238875649")
            )
        )

        contactUsAdapter = ContactUsAdapter(teamList)
        cardsRV.adapter = contactUsAdapter
        contactUsAdapter.notifyDataSetChanged()

        binding.back.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.map.setOnClickListener {
            val url =
                "https://www.google.com/maps/place/JSS+Academy+of+Technical+Education/@28.6141105,77.3562014,17z/data=!3m1!4b1!4m6!3m5!1s0x390ce5992452d761:0xaaa44725147c1507!8m2!3d28.6141105!4d77.3587763!16s%2Fm%2F05c14tc?entry=ttu"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }
}
