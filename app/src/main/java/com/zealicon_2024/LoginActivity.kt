package com.zealicon_2024

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.zealicon_2024.utils.RemoteConfigHelper
import com.zealicon_2024.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var tokenManager: TokenManager
    public var isLogin = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        RemoteConfigHelper.fetchAndActivate()

        if(tokenManager.getToken() != null && tokenManager.getToken() != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        //this is a comment
    }
}