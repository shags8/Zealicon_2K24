package com.zealicon_2024

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zealicon_2024.api.SignupAPI
import com.zealicon_2024.databinding.FragmentSigninBinding
import com.zealicon_2024.di.NetworkModule
import com.zealicon_2024.models.LoginRequest
import com.zealicon_2024.models.LoginResponse
import com.zealicon_2024.utils.TokenManager
import com.zealicon_2024.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.sign

@AndroidEntryPoint
class SigninFragment : Fragment() {
    private var _binding: FragmentSigninBinding? = null
    private val signupViewModel by viewModels<SignupViewModel>()
    @Inject
    lateinit var signupAPI: SignupAPI
    @Inject
    lateinit var tokenManager: TokenManager

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)
       // signupAPI = NetworkModule.provideSignupAPI()

        binding.loginButton.setOnClickListener {
            val number = binding.inputNumber.text.toString()
            if (number.isNotEmpty() && number.length == 10) {
                binding.loginButton.isEnabled = false
                binding.progressBar.isVisible = true
                CoroutineScope(Dispatchers.IO).launch {
                    val response = signupAPI.loginUser(LoginRequest(number))
                    response.enqueue(object : Callback<LoginResponse> {
                        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                            if (response.isSuccessful && response.body() != null) {
                                binding.progressBar.isVisible = false
                                binding.loginButton.isEnabled = true
                                Log.e("loginReponse", "${response.body()}")
                                Log.e("loginRespone", "${response}")
                                // Log.e("loginRespone")
                                if (response.body()!!.message == "An otp is sent to email successfully.") {
                                    Toast.makeText(context,"OTP sent successfully",Toast.LENGTH_SHORT).show()
                                    findNavController().navigate(R.id.action_signinFragment_to_OTPFragment)
                                }
                                tokenManager.savePhoneNumber(number)

                            } else if (response.errorBody() != null) {
                                val errObj = JSONObject(response.errorBody()!!.charStream().readText())
                                binding.progressBar.isVisible = false
                                binding.loginButton.isEnabled = true
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
//                signupViewModel.loginUser(LoginRequest(number) , findNavController() , requireContext())
                binding.progressBar.isVisible = false
                Log.e("id123", number)
           }
            else {
                binding.progressBar.isVisible = false
                binding.loginButton.isEnabled = true
                Toast.makeText(
                    activity as LoginActivity,
                    "Please provide valid phone number",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //   findNavController().navigate(R.id.action_signinFragment_to_OTPFragment)
        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}