package com.zealicon_2024.models

import android.telephony.SmsMessage

data class GetZealResponse(
    val success: Boolean,
    val message: String,
    val zeal_id:String,
    val userData: UserData
)

data class UserData (
    val name: String,
    val secure_url: String
)