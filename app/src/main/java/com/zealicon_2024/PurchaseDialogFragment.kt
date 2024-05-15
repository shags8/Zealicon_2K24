package com.zealicon_2024

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.razorpay.Checkout
import com.razorpay.ExternalWalletListener
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.zealicon_2024.api.PaymentAPI
import com.zealicon_2024.databinding.FragmentPurchaseDialogBinding
import com.zealicon_2024.models.OrderRequest
import com.zealicon_2024.utils.Constants.TAG
import com.zealicon_2024.utils.Constants.isPayDone
import com.zealicon_2024.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import kotlin.math.log
@AndroidEntryPoint
class PurchaseDialogFragment : BottomSheetDialogFragment(){
    private var _binding : FragmentPurchaseDialogBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var paymentAPI: PaymentAPI
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentPurchaseDialogBinding.inflate(inflater, container, false)

        binding.continueText.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            activity?.finish()
        }

        if(isPayDone == 2){
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

        if(isPayDone == 3){
            binding.background.setImageResource(R.drawable.background_popup_3)
            binding.success.isVisible = true
            binding.success.setImageResource(R.drawable.failed)
            binding.paymentHead.isVisible = true
            binding.paymentHead.text = "Payment Failed"
            binding.purchaseDesc.text = "Oops! If your money has been deducted then please contact the team."
            binding.continueText.text = "Support"
            binding.verifyHead.isVisible = false
            binding.purchaseHead.isVisible = false
            binding.zealAmount.isVisible = false
            binding.payButton.setImageResource(R.drawable.retry_button)
        }


        binding.payButton.setOnClickListener {
            binding.payButton.isEnabled = false
            if(isPayDone == 1 || isPayDone == 3){
                CoroutineScope(Dispatchers.IO).launch {
                    val token = tokenManager.getToken().toString()
                    Log.d("KING123", "$token")
                    val res = paymentAPI.getOrderID(OrderRequest(token))
                    Log.d("KING", "onCreateView: ${res.body()?.order?.id}")
                    val orderId = res.body()?.order?.id
                    val intent = Intent(requireContext(), PaymentActivity::class.java)
                    binding.payButton.isEnabled = true
                    intent.putExtra("id", orderId)
                    startActivity(intent)
                }
//                startActivity(Intent(requireContext(), PaymentActivity::class.java))
            }else{
                binding.payButton.isEnabled = true
                startActivity(Intent(requireContext(), MainActivity::class.java))
                activity?.finish()
            }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        binding.payButton.isEnabled = true
        if(isPayDone == 2){
            binding.background.setImageResource(R.drawable.background_popup_2)
            binding.success.isVisible = true
            binding.paymentHead.isVisible = true
            binding.yourZeal.isVisible = true
            binding.zealId.isVisible = true
            binding.continueText.isVisible = false
            binding.payButton.setImageResource(R.drawable.home_button)
            binding.success.setImageResource(R.drawable.success)
            binding.purchaseDesc.isVisible = false
            binding.purchaseHead.isVisible = false
            binding.verifyHead.isVisible = false
            binding.zealAmount.isVisible = false
        }

        if(isPayDone == 3){
            binding.background.setImageResource(R.drawable.background_popup_3)
            binding.success.isVisible = true
            binding.success.setImageResource(R.drawable.failed)
            binding.paymentHead.isVisible = true
            binding.paymentHead.text = "Payment Failed"
            binding.purchaseDesc.text = "Oops! If your money has been deducted then please contact the team."
            binding.continueText.text = "Support"
            binding.verifyHead.isVisible = false
            binding.purchaseHead.isVisible = false
            binding.zealAmount.isVisible = false
            binding.payButton.setImageResource(R.drawable.retry_button)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initiatePayment(){
        val co = Checkout()
        co.setKeyID("rzp_test_QO79q5On7PHekR")
        try{
            val option = JSONObject()
            option.put("name", "Zealicon 2024")
            option.put("currency", "INR")
            option.put("amount", "30000")
//            val prefill = JSONObject()
//            prefill.put("email", "ayushmaserati@gmail.com")
//            prefill.put("contact", "9870804246")
//
//            option.put("prefill", prefill)

            co.open(activity, option)

        }catch (e: Exception) {
            Log.d(TAG, e.toString())
            Toast.makeText(requireContext(), "some error occured", Toast.LENGTH_SHORT).show()
        }
    }
//    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
//        Toast.makeText(requireContext(), "Payment Successful", Toast.LENGTH_SHORT).show()
//        try{
//            isPayDone = true
//            binding.background.setImageResource(R.drawable.background_popup_2)
//            binding.success.isVisible = true
//            binding.paymentHead.isVisible = true
//            binding.yourZeal.isVisible = true
//            binding.zealId.isVisible = true
//            binding.continueText.isVisible = false
//            binding.payButton.setImageResource(R.drawable.home_button)
//            binding.purchaseDesc.isVisible = false
//            binding.purchaseHead.isVisible = false
//            binding.verifyHead.isVisible = false
//            binding.zealAmount.isVisible = false
//        }catch (e: Exception){
//            Log.d(TAG, e.toString())
//        }
//
//    }
//
//    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
//        binding.background.setImageResource(R.drawable.background_popup_3)
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
//    }
}