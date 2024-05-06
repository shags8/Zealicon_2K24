package com.zealicon_2024

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zealicon_2024.databinding.FragmentPurchaseDialogBinding

class PurchaseDialogFragment : BottomSheetDialogFragment() {
    private var _binding : FragmentPurchaseDialogBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPurchaseDialogBinding.inflate(inflater, container, false)

        binding.payButton.setOnClickListener {
            binding.background.setImageResource(R.drawable.background_popup_2)
            binding.success.isVisible = true
            binding.paymentHead.isVisible = true
            binding.yourZeal.isVisible = true
            binding.zealId.isVisible = true
            binding.continueText.isVisible = false
            binding.payButton.setImageResource(R.drawable.home_button)
            binding.purchaseDesc.isVisible = false
            binding.purchaseHead.isVisible = false
            binding.verifyHead.isVisible = false
            binding.zealAmount.isVisible = false
        }
//        binding.payButton.setOnClickListener {
//            binding.background.setImageResource(R.drawable.background_popup_3)
//            binding.success.isVisible = true
//            binding.success.setImageResource(R.drawable.failed)
//            binding.paymentHead.isVisible = true
//            binding.paymentHead.text = "Payment Failed"
//            binding.purchaseDesc.text = "Oops! If your money has been deducted then please contact the team."
//            binding.continueText.text = "Support"
//            binding.verifyHead.isVisible = false
//            binding.purchaseHead.isVisible = false
//            binding.zealAmount.isVisible = false
//            binding.payButton.setImageResource(R.drawable.retry_button)
//        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}