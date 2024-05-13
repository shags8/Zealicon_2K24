package com.zealicon_2024.models

data class LoginRequest (
    val phone: String
)

data class LoginResponse(
    val email: String,
    val message: String,
    val success: Boolean
)