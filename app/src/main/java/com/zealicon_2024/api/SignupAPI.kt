package com.zealicon_2024.api

import com.zealicon_2024.models.LoginRequest
import com.zealicon_2024.models.LoginResponse
import com.zealicon_2024.models.OTPVerifyRequest
import com.zealicon_2024.models.OTPVerifyResponse
import com.zealicon_2024.models.SignupRequest
import com.zealicon_2024.models.SignupResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupAPI {
    @POST("/api/auth/signup")
    suspend fun signupUser(@Body signupRequest: SignupRequest): Response<SignupResponse>

    @POST("/api/auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/auth/verify-otp")
    fun verifyOTP(@Body otpVerifyRequest: OTPVerifyRequest):Call<OTPVerifyResponse>

}