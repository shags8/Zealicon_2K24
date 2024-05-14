package com.zealicon_2024

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.zealicon_2024.databinding.ActivityZealTicketBinding
import com.zealicon_2024.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ZealTicketActivity : AppCompatActivity() {
    @Inject
    lateinit var tokenManager : TokenManager
    private lateinit var binding: ActivityZealTicketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityZealTicketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val zealId = tokenManager.getZeal()
        val name = tokenManager.getName()
        val image = tokenManager.getID()

        binding.zealId.text = zealId
        binding.studentName.text = name

        Glide.with(this)
            .load(image)
            .into(binding.idCard)

        binding.back.setOnClickListener {
            finish()
        }
    }
}