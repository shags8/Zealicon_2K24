package com.zealicon_2024.models

data class ResendOTP(
    val phone:String
)
data class ResendOTPResponse(
    val success:Boolean,
    val message:String
)
