package com.zealicon_2024.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.zealicon_2024.R
import com.zealicon_2024.api.SignupAPI
import com.zealicon_2024.models.LoginRequest
import com.zealicon_2024.models.LoginResponse
import com.zealicon_2024.models.OTPVerifyRequest
import com.zealicon_2024.models.OTPVerifyResponse
import com.zealicon_2024.models.SignupRequest
import com.zealicon_2024.models.SignupResponse
import com.zealicon_2024.utils.Constants.TAG
import com.zealicon_2024.utils.NetworkResult
import com.zealicon_2024.utils.TokenManager
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class SignupRepository @Inject constructor(private val signupAPI: SignupAPI ,
                                           private val tokenManager: TokenManager) {

    private val _signupLiveData = MutableLiveData<NetworkResult<SignupResponse>>()
    val signupLiveData: LiveData<NetworkResult<SignupResponse>> get() = _signupLiveData

    suspend fun signupUser(signupRequest: SignupRequest) {
        _signupLiveData.postValue(NetworkResult.Loading())
        val response = signupAPI.signupUser(signupRequest)
        Log.d("repo", response.body().toString())
        Log.d("repo", response.toString())
        if (response.isSuccessful && response.body() != null) {
            Log.d(TAG, "signupUser: YES")
            tokenManager.savePhoneNumber(signupRequest.phone)
            _signupLiveData.postValue(NetworkResult.Success(response.body()))
        } else if (response.errorBody() != null) {

            val errObj = JSONObject(response.errorBody()!!.charStream().readText())
            Log.d(TAG, errObj.toString())
            _signupLiveData.postValue(NetworkResult.Error(errObj.getString("message")))
        } else {
            _signupLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }

    fun loginUser(loginRequest: LoginRequest, navController: NavController, context: Context) {
        Log.e("id123", "loginUser called")
        val response = signupAPI.loginUser(loginRequest)
        Log.e("id123", "loginUser called")

        response.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {


                if (response.isSuccessful && response.body() != null) {
                    Log.e("loginRespone", "${response.body()}")
                    Log.e("loginRespone", "${response}")
                    if (response.body()!!.message == "An otp is sent to email successfully.") {
                        Toast.makeText(context,"OTP sent successfully",Toast.LENGTH_SHORT).show()
                        navController.navigate(R.id.action_signinFragment_to_OTPFragment)
                    }
                    tokenManager.savePhoneNumber(loginRequest.phone)

                } else if (response.errorBody() != null) {
                    val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                    Log.e("error body", errObj.toString())
                    Log.e("error body", errObj.getString("message"))
                    Log.e("error body", errObj.getString("success"))
                    Toast.makeText(context, "User not Found!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("failureResponse", "${t.message}")
                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()

            }
        })
    }

    fun verifyOTP(otpVerifyRequest: OTPVerifyRequest , navController: NavController, context: Context) {
        val response = signupAPI.verifyOTP(otpVerifyRequest)
        response.enqueue(object : Callback<OTPVerifyResponse> {
            override fun onResponse(
                call: Call<OTPVerifyResponse>,
                response: Response<OTPVerifyResponse>
            ) {
                if(response.isSuccessful && response.body()!=null){
                    if(response.body()!!.message=="Verified successfully") {
                        Log.e("otpResponse", "${response.body()}")
                        tokenManager.saveToken(response.body()!!.token)
                        Toast.makeText(context, "OTP verified", Toast.LENGTH_SHORT).show()
                        Log.e("token", "${tokenManager.getToken()}")

                    }
                }else if (response.errorBody() != null){
                    val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                    Log.e("error body", errObj.toString())
                    Log.e("error body", errObj.getString("message"))
                    Log.e("error body", errObj.getString("success"))
                    Toast.makeText(context, "OTP not verified", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<OTPVerifyResponse>, t: Throwable) {
                Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_SHORT).show()

            }
        })
    }
}