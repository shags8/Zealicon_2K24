package com.zealicon_2024.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.zealicon_2024.models.LoginRequest
import com.zealicon_2024.models.OTPVerifyRequest
import com.zealicon_2024.models.SignupRequest
import com.zealicon_2024.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(private val signupRepository: SignupRepository): ViewModel() {
    val signupLiveData = signupRepository.signupLiveData

    fun signupUser(signupRequest: SignupRequest){
        viewModelScope.launch {
            signupRepository.signupUser(signupRequest)
        }
    }

    fun loginUser(loginRequest: LoginRequest , navController: NavController , context: Context){
        Log.e("id123","viewModel called")
            signupRepository.loginUser(loginRequest,navController,context)
    }

    fun verifyOTP(otpVerifyRequest: OTPVerifyRequest , context: Context , navController: NavController){
        signupRepository.verifyOTP(otpVerifyRequest,navController,context)
    }
}