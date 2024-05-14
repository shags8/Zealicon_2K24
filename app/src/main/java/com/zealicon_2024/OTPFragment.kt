package com.zealicon_2024

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zealicon_2024.databinding.FragmentOTPBinding
import com.zealicon_2024.models.OTPVerifyRequest
import com.zealicon_2024.utils.TokenManager
import com.zealicon_2024.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPFragment : Fragment() {
    private var _binding: FragmentOTPBinding? = null

    private val binding get() = _binding!!
    private val signupViewModel by viewModels<SignupViewModel>()
    lateinit var tokenManager : TokenManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOTPBinding.inflate(inflater, container, false)
        tokenManager = TokenManager(requireContext())

        binding.submitButton.setOnClickListener {
            val otp=binding.otp.text.toString()
            if(otp.length==6){
                val phone=tokenManager.getPhoneNumber()
                signupViewModel.verifyOTP(OTPVerifyRequest(phone.toString(),otp), requireContext(),findNavController())
                val purchaseDialogPopup = PurchaseDialogFragment()
                purchaseDialogPopup.show(childFragmentManager, "BSDialogFragment")
            }else{
                Toast.makeText(requireContext(),"Enter valid OTP",Toast.LENGTH_SHORT).show()
            }

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}