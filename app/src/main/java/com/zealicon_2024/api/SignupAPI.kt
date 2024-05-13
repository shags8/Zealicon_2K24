package com.zealicon_2024.api

import com.zealicon_2024.models.SignupRequest
import com.zealicon_2024.models.SignupResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupAPI {
    @POST("/api/auth/signup")
    suspend fun signupUser(@Body signupRequest: SignupRequest): Response<SignupResponse>

//    @POST("/api/auth/login")
//    suspend fun loginUser(@Body )
}