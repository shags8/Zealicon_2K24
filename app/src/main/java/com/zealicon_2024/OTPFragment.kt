package com.zealicon_2024

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zealicon_2024.api.SignupAPI
import com.zealicon_2024.databinding.FragmentOTPBinding
import com.zealicon_2024.models.OTPVerifyRequest
import com.zealicon_2024.models.OTPVerifyResponse
import com.zealicon_2024.models.ResendOTP
import com.zealicon_2024.models.ResendOTPResponse
import com.zealicon_2024.utils.TokenManager
import com.zealicon_2024.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Callback
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import kotlin.text.Typography.dagger

@AndroidEntryPoint
class OTPFragment : Fragment() {
    private var _binding: FragmentOTPBinding? = null

    private val binding get() = _binding!!
    private val signupViewModel by viewModels<SignupViewModel>()
    lateinit var tokenManager : TokenManager
    @Inject
    lateinit var signupAPI: SignupAPI


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOTPBinding.inflate(inflater, container, false)
        tokenManager = TokenManager(requireContext())

        binding.submitButton.setOnClickListener {
            val otp=binding.otp.text.toString()
            if(otp.length==6){
                val phone=tokenManager.getPhoneNumber().toString()
                CoroutineScope(Dispatchers.IO).launch {
                    val response = signupAPI.verifyOTP(OTPVerifyRequest(phone, otp))
                    response.enqueue(object : retrofit2.Callback<OTPVerifyResponse> {
                        override fun onResponse(
                            call: Call<OTPVerifyResponse>,
                            response: Response<OTPVerifyResponse>
                        ) {
                            if(response.isSuccessful && response.body()!=null){
                                if(response.body()!!.message=="Verified successfully") {
                                    Log.e("otpResponse", "${response.body()}")
                                    tokenManager.saveToken(response.body()!!.token)
                                    tokenManager.saveUserId(response.body()!!._id)
                                    Toast.makeText(context, "OTP verified", Toast.LENGTH_SHORT).show()
                                    Log.e("token", "${tokenManager.getToken()}")
                                    Log.e("_id", "${tokenManager.getUserId()}")
                                    val activity = activity as LoginActivity
                                    if(activity.isLogin == 1){
                                        startActivity(Intent(requireContext(), MainActivity::class.java))
                                        getActivity()?.finish()
                                    }else{
                                        val purchaseDialogPopup = PurchaseDialogFragment()
                                        purchaseDialogPopup.show(childFragmentManager, "BSDialogFragment")
                                    }
                                }
                            }else if (response.errorBody() != null){
                                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                                Log.e("error body", errObj.toString())
                                Log.e("error body", errObj.getString("message"))
                                Log.e("error body", errObj.getString("success"))
                                Toast.makeText(context, "OTP not correct", Toast.LENGTH_SHORT).show()
                            }


                        }

                        override fun onFailure(call: Call<OTPVerifyResponse>, t: Throwable) {
                            Toast.makeText(context, "Something Went Wrong!", Toast.LENGTH_SHORT).show()

                        }
                    })
                }
//                signupViewModel.verifyOTP(OTPVerifyRequest(phone.toString(),otp), requireContext(),findNavController())
            }else{
                Toast.makeText(requireContext(),"Enter valid OTP",Toast.LENGTH_SHORT).show()
            }

        }

        binding.resend.setOnClickListener {
            val phone = tokenManager.getPhoneNumber().toString()
            // CoroutineScope(Dispatchers.IO).launch {
            val response =
                signupAPI.resendOTP(ResendOTP(phone))
            response.enqueue(object : retrofit2.Callback<ResendOTPResponse> {
                override fun onResponse(
                    call: Call<ResendOTPResponse>,
                    response: Response<ResendOTPResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        Toast.makeText(requireContext(),"OTP sent successfully",Toast.LENGTH_SHORT).show()
                        Log.e("resendOtp", "${response.body()}")
                    } else if (response.errorBody() != null) {
                        val errObj =
                            JSONObject(response.errorBody()!!.charStream().readText())
                        Log.e("error body", errObj.toString())
                        Log.e("error body", errObj.getString("message"))
                        Log.e("error body", errObj.getString("success"))
                        Toast.makeText(context, "OTP not correct", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResendOTPResponse>, t: Throwable) {
                    Toast.makeText(requireContext(),"Something Went Wrong!",Toast.LENGTH_SHORT).show()
                }


            })
        }
        //  }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}