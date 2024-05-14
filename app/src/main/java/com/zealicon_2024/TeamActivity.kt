package com.zealicon_2024

import TeamAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zealicon_2024.databinding.ActivityTeamBinding
import com.zealicon_2024.databinding.TeamItemBinding

class TeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding
    lateinit var teamAdapter: TeamAdapter
    lateinit var teamArray: ArrayList<TeamMember>
    private val db = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        teamArray = arrayListOf()

        teamAdapter = TeamAdapter(this, teamArray)
        binding.coreTeamRV.adapter = teamAdapter

        val eventRef = db.child("team").child("data")
        eventRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val name = it.child("name").getValue(String::class.java).toString()
                    val image = it.child("image").getValue(String::class.java).toString()

                    val eventData = TeamMember(name, image)

                    teamArray.add(eventData)
                }

                teamAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        binding.back.setOnClickListener {
            startActivity(Intent(this , MenuActivity::class.java))
            finish()
        }
    }
}