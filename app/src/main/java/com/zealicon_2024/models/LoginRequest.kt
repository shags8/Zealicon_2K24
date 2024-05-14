package com.zealicon_2024.models

data class LoginRequest (
    val phone: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val email: String
)
