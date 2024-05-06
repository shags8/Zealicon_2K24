package com.zealicon_2k24

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zealicon_2k24.databinding.FragmentOTPBinding

class OTPFragment : Fragment() {
    private var _binding: FragmentOTPBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentOTPBinding.inflate(inflater, container, false)

        binding.submitButton.setOnClickListener {
            val purchaseDialogPopup = PurchaseDialogFragment()
            purchaseDialogPopup.show(childFragmentManager, "BSDialogFragment")
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}