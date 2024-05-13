package com.zealicon_2024

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zealicon_2024.databinding.FragmentSigninBinding

class SigninFragment : Fragment() {
    private var _binding: FragmentSigninBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSigninBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            findNavController().navigate(R.id.action_signinFragment_to_OTPFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}