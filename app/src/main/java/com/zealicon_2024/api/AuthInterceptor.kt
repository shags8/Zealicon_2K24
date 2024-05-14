package com.zealicon_2024.api

import android.util.Base64
import com.zealicon_2024.utils.TokenManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(): Interceptor {
    @Inject
    lateinit var tokenManager: TokenManager

    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request().newBuilder()
//
//        val token = tokenManager.getToken()

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Basic " + getAuthToken())
            .build()

//        request.addHeader("Authorization", "Bearer $token")

        return chain.proceed(request)
    }

    private fun getAuthToken(): Any? {
        val username = "rzp_live_XFKbiwn0rPna6O"
        val password = "jKEXzMHPs3g3CszcX5Eg7b5i"
        val credentials = "$username:$password"
        return Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
    }
}