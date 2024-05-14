package com.zealicon_2024.models

data class OrderResponse2(
    val message: String,
    val order: Order,
    val success: Boolean
)