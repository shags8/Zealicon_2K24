package com.zealicon_2024.utils
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

object RemoteConfigHelper {

    private val firebaseRemoteConfig by lazy { Firebase.remoteConfig }

    fun fetchAndActivate() {
        firebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Remote Config fetch and activate succeeded
                    updateConstants()
                } else {

                }
            }
    }

    private fun updateConstants() {
        Constants.BASE_URL = firebaseRemoteConfig.getString("BASE_URL")
        Constants.RAZORPAY_KEY = firebaseRemoteConfig.getString("RAZORPAY_KEY")
    }
}