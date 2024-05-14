package com.zealicon_2024

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
import com.zealicon_2024.databinding.FragmentSigninBinding
import com.zealicon_2024.di.NetworkModule
import com.zealicon_2024.models.LoginRequest
import com.zealicon_2024.models.LoginResponse
import com.zealicon_2024.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.sign

@AndroidEntryPoint
class SigninFragment : Fragment() {
    private var _binding: FragmentSigninBinding? = null
    private val signupViewModel by viewModels<SignupViewModel>()

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
                signupViewModel.loginUser(LoginRequest(number) , findNavController() , requireContext())
                Log.e("id123", number)
           }
            else {
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