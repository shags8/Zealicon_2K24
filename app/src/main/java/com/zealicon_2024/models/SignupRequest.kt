package com.zealicon_2024.models

data class SignupRequest(
    val email: String,
    val id_card: String,
    val name: String,
    val phone: String,
)

data class SignupResponse(
    val success: Boolean,
    val message: String
)