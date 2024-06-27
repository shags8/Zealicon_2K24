package com.zealicon_2024

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import com.zealicon_2024.api.SignupAPI
import com.zealicon_2024.models.PaymentVerifyRequest
import com.zealicon_2024.models.SignupResponse
import com.zealicon_2024.utils.Constants
import com.zealicon_2024.utils.Constants.RAZORPAY_KEY
import com.zealicon_2024.utils.Constants.isPayDone
import com.zealicon_2024.utils.RemoteConfigHelper
import com.zealicon_2024.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.log

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity(), PaymentResultWithDataListener {
    @Inject
    lateinit var signupAPI: SignupAPI

    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_payment)

        RemoteConfigHelper.fetchAndActivate()

        val orderId = intent.getStringExtra("id")
        val key = RAZORPAY_KEY
        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID(key)
        try{
            val option = JSONObject()
            option.put("name", "Zealicon")
            option.put("currency", "INR")
            option.put("image", "https://firebasestorage.googleapis.com/v0/b/zealicon-2k24.appspot.com/o/zeal.png?alt=media&token=ee6c4743-dffc-49d7-8ddf-30f4e16349da")
            option.put("theme.color", "#8D27CB")
            val methodOBJ= JSONObject()
            val upiOBJ = JSONObject()
            upiOBJ.put("intent",false)
            methodOBJ.put("upi",upiOBJ)
            methodOBJ.put("card",false)
            methodOBJ.put("netbanking",false)
            methodOBJ.put("wallet",false)
            methodOBJ.put("bank_transfer",false)
            methodOBJ.put("paylater",false)
            methodOBJ.put("emi",false)
            option.put("method",methodOBJ)
            option.put("order_id", "$orderId")
//            val prefill = JSONObject()
//            prefill.put("email", "ayushmaserati@gmail.com")
//            prefill.put("contact", "9870804246")
//
//            option.put("prefill", prefill)

            co.open(this, option)

        }catch (e: Exception) {
            Log.d(Constants.TAG, e.toString())
            Toast.makeText(this, "some error occured", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {

        Log.d("KING", "onPaymentSuccess: ${p1?.paymentId}, ${p1?.orderId}, ${p1?.signature}, ${p1?.data} ")
        var res : Response<SignupResponse>? = null
        CoroutineScope(Dispatchers.IO).launch {
            val _id = tokenManager.getUserId().toString()
            Log.d("KING123", _id)
            res = signupAPI.paymentVerify(_id, PaymentVerifyRequest(p1!!.orderId, p1.paymentId, p1.signature))
        }
//        if(res!!.body()!!.success){
            CoroutineScope(Dispatchers.IO).launch {
                val token = tokenManager.getToken().toString()
                Log.d("KING", "$token")
                val response = signupAPI.getZealId(token)
                val zeal = response.body()?.zeal_id
                val idCard = response.body()?.userData?.secure_url
                val name = response.body()?.userData?.name
                tokenManager.saveZeal(zeal)
                tokenManager.saveName(name)
                tokenManager.saveID(idCard)
                isPayDone = 2
                finish()
            }
//        }

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        isPayDone = 3
        finish()
    }
}