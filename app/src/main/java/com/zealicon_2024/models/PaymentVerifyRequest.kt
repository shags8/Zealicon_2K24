package com.zealicon_2024.models

data class PaymentVerifyRequest(
    val razorpay_order_id: String,
    val razorpay_payment_id: String,
    val razorpay_signature: String
)
