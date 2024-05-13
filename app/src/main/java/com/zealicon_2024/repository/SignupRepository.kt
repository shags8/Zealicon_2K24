package com.zealicon_2024.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zealicon_2024.api.SignupAPI
import com.zealicon_2024.models.SignupRequest
import com.zealicon_2024.models.SignupResponse
import com.zealicon_2024.utils.Constants.TAG
import com.zealicon_2024.utils.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class SignupRepository @Inject constructor(private val signupAPI: SignupAPI) {

    private val _signupLiveData = MutableLiveData<NetworkResult<SignupResponse>>()
    val signupLiveData: LiveData<NetworkResult<SignupResponse>> get() = _signupLiveData


    suspend fun signupUser(signupRequest: SignupRequest){
        _signupLiveData.postValue(NetworkResult.Loading())
        val response = signupAPI.signupUser(signupRequest)
        Log.d("repo", response.body().toString())
        Log.d("repo", response.toString())
        if (response.isSuccessful && response.body() != null) {
            Log.d(TAG, "signupUser: YES")
            _signupLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {

            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            Log.d(TAG, errObj.toString())
            _signupLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
        } else {
            _signupLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

}