package com.zealicon_2024

import android.content.Intent
import android.net.Uri
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
        }

        binding.shareAppCard.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                val URL = "https://play.google.com/store/apps/details?id=com.zealicon_2024"
                putExtra(Intent.EXTRA_TEXT, "Check out aur ZEALICON 2024 App: $URL" )
            }
            startActivity(Intent.createChooser(shareIntent, "Share Via"))
        }

        binding.followUsCard.setOnClickListener {
            val instagramUri = Uri.parse("https://www.instagram.com/zealicon_2k24/")
            val intent = Intent(Intent.ACTION_VIEW, instagramUri)

            // Set the package to Instagram if available, otherwise, open in a web browser
            intent.setPackage("com.instagram.android")

            // If Instagram app is not available, open the link in a web browser
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                // If Instagram app is not available, open the link in a web browser
                intent.setPackage(null)
                startActivity(intent)
            }
        }
    }

}