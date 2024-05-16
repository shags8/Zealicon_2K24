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
    lateinit var teamAdapter2: TeamAdapter
    lateinit var teamAdapter3: TeamAdapter
    lateinit var teamArray: ArrayList<TeamMember>
    lateinit var coreArray:ArrayList<TeamMember>
    lateinit var techTeam:ArrayList<TeamMember>
    private val db = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        teamArray = arrayListOf()
        coreArray= arrayListOf()
        techTeam= arrayListOf()
        teamAdapter = TeamAdapter(this, teamArray)
        teamAdapter2=TeamAdapter(this,coreArray)
        teamAdapter3=TeamAdapter(this,techTeam)
        binding.managementTeam.adapter = teamAdapter
        binding.rv2.adapter=teamAdapter2
        binding.rv3.adapter=teamAdapter3

        val eventRef = db.child("team").child("data")
        eventRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val role=it.child("role").getValue(String::class.java).toString()
                    val name = it.child("name").getValue(String::class.java).toString()
                    val image = it.child("image").getValue(String::class.java).toString()
                    val eventData = TeamMember(role,name, image)

                    teamArray.add(eventData)
                }

                teamAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val eventRef2 = db.child("team").child("coreTeam")
        eventRef2.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val role=it.child("role").getValue(String::class.java).toString()
                    val name = it.child("name").getValue(String::class.java).toString()
                    val image = it.child("image").getValue(String::class.java).toString()
                    val eventData = TeamMember(role,name, image)
                    coreArray.add(eventData)
                }

                teamAdapter2.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val eventRef3 = db.child("team").child("techTeam")
        eventRef3.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val role=it.child("role").getValue(String::class.java).toString()
                    val name = it.child("name").getValue(String::class.java).toString()
                    val image = it.child("image").getValue(String::class.java).toString()
                    val eventData = TeamMember(role,name, image)

                    techTeam.add(eventData)
                }

                teamAdapter3.notifyDataSetChanged()

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