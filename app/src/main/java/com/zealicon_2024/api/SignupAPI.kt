package com.zealicon_2024.api

import com.zealicon_2024.models.LoginRequest
import com.zealicon_2024.models.LoginResponse
import com.zealicon_2024.models.GetZealResponse
import com.zealicon_2024.models.OTPVerifyRequest
import com.zealicon_2024.models.OTPVerifyResponse
import com.zealicon_2024.models.PaymentVerifyRequest
import com.zealicon_2024.models.ResendOTP
import com.zealicon_2024.models.ResendOTPResponse
import com.zealicon_2024.models.SignupRequest
import com.zealicon_2024.models.SignupResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface SignupAPI {
    @POST("/api/auth/signup")
    suspend fun signupUser(@Body signupRequest: SignupRequest): Response<SignupResponse>

    @POST("/api/auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/auth/verify-otp")
    fun verifyOTP(@Body otpVerifyRequest: OTPVerifyRequest):Call<OTPVerifyResponse>

    @POST("/api/payment/payment-verification/{id}/")
    suspend fun paymentVerify(
        @Path("id") id: String,
        @Body paymentVerifyRequest: PaymentVerifyRequest,
    ): Response<SignupResponse>

    @GET("/api/payment/get-zeal-id/{token}/")
    suspend fun getZealId(@Path("token") token: String): Response<GetZealResponse>

    @PATCH("/api/auth/resend-otp")
    fun resendOTP(@Body resendOTP: ResendOTP):Call<ResendOTPResponse>
}