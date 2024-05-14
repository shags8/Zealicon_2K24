package com.zealicon_2024.api

import com.zealicon_2024.models.OrderRequest
import com.zealicon_2024.models.OrderResponse
import com.zealicon_2024.models.OrderResponse2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentAPI {
    @POST("/api/payment/checkout")
    suspend fun getOrderID(@Body orderRequest: OrderRequest): Response<OrderResponse2>

}