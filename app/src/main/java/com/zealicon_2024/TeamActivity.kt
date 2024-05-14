package com.zealicon_2024

import TeamAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zealicon_2024.databinding.ActivityTeamBinding
import com.zealicon_2024.databinding.TeamItemBinding

class TeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding
    lateinit var teamAdapter: TeamAdapter
    lateinit var teamArray: ArrayList<TeamMember>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        teamArray = arrayListOf()

        for (i in 1..6)

            teamArray.add(
                TeamMember(
                    "Ayush Agrawal", "Technical Head"
                )
            )

        teamAdapter = TeamAdapter(this, teamArray)
        binding.coreTeamRV.adapter = teamAdapter
        binding.manageTeamRV.adapter = teamAdapter

        binding.back.setOnClickListener {
            startActivity(Intent(this , MenuActivity::class.java))
            finish()
        }
    }
}