package com.zealicon_2024

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zealicon_2024.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener {
            finish()
        }

        binding.teamCard.setOnClickListener{
            startActivity(Intent(this, TeamActivity::class.java))
            finish()
        }
        binding.ContactUsCard.setOnClickListener{
            startActivity(Intent(this,ContactUs::class.java))
            finish()
        }
        binding.aboutUsCard.setOnClickListener{
            startActivity(Intent(this,AboutActivity::class.java))
            finish()
        }
    }

}