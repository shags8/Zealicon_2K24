package com.zealicon_2024.models

data class OTPVerifyRequest (
    val phone: String,
    val otp: String
)

data class OTPVerifyResponse(
    val _id: String,
    val message: String,
    val success: Boolean,
    val token: String
)