package com.zealicon_2024.models

data class OTPVerifyRequest (
    val email: String,
    val otp: Int
)

data class OTPVerifyResponse(
    val _id: String,
    val message: String,
    val success: Boolean,
    val token: String
)