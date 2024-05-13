package com.zealicon_2024.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
}