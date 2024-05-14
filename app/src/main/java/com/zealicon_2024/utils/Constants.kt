package com.zealicon_2024.utils

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig

object Constants {
//    val remoteConfig = Firebase.remoteConfig
//    val configSettings = remoteConfigSettings {
//        minimumFetchIntervalInSeconds = 3600
//    }
//    remoteConfig.setConfigSettingsAsync(configSettings)
//
    val BASE_URL = "https://zealicon-api-24.onrender.com/"
    val BASE_URL2 = "https://api.razorpay.com/"
    val TAG = "ZEALICON"
    const val PREFS_TOKEN_FILE = "PREFS_TOKEN_FILE"
    const val USER_TOKEN = "USER_TOKEN"
    const val ROLE = "ROLE"
    const val PHONE_NUMBER="PHONE_NUMBER"
    const val ZEAL_ID = "ZEAL_ID"
    const val NAME = "NAME"
    const val ID_CARD = "ID_CARD"
    const val USER_ID = "USER_ID"
    var isPayDone = 1
}
