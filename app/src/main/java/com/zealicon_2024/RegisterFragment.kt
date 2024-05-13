package com.zealicon_2024

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.zealicon_2024.api.SignupAPI
import com.zealicon_2024.databinding.FragmentRegisterBinding
import com.zealicon_2024.models.SignupRequest
import com.zealicon_2024.utils.Constants.TAG
import com.zealicon_2024.utils.NetworkResult
import com.zealicon_2024.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val signupViewModel by viewModels<SignupViewModel>()
    private val CAM_PERM_CODE = 101
    private var defaultImageView: ImageView? = null
    private var imageAdded = false
    private var currentImageView: ImageView? = null
    lateinit var imageUri: Uri
    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            currentImageView!!.setImageURI(imageUri)
        } else {
            currentImageView!!.setImageResource(R.drawable.upload_id)
        }
//        currentImageView!!.setImageURI(null)
    }
    private var finalEmail = ""

    @Inject
    lateinit var api: SignupAPI

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            val name = binding.inputName.text.toString()
            val phone = binding.inputPhone.text.toString()
            val email = binding.inputEmail.text.toString()
            val admNo = binding.inputAdmNo.text.toString()
            val imageString = convertImageToBase64(binding.inputId)
            val image = "data:image/png;base64,$imageString"

            if (name.isNotEmpty() && phone.isNotEmpty() && admNo.isNotEmpty() && email.isNotEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()) {
                    finalEmail = email
                } else {
                    Toast.makeText(
                        activity as LoginActivity,
                        "Please provide valid email",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                if(phone.length < 10){
                    Toast.makeText(
                        activity as LoginActivity,
                        "Please provide valid phone number",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } else {
                Toast.makeText(
                    activity as LoginActivity,
                    "Please provide valid information",
                    Toast.LENGTH_SHORT
                ).show()
            }

            signupViewModel.signupUser(SignupRequest(finalEmail, image, name, phone))
        }

        currentImageView = binding.inputId

        binding.inputId.setOnClickListener {
            askCameraPermission()
        }

        imageUri = createImageUri()

        bindObserver()

    }


    fun bindObserver() {
        signupViewModel.signupLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            binding.transparentBg.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    Toast.makeText(requireContext(), it.data!!.message, Toast.LENGTH_SHORT).show()
                    signupViewModel.signupLiveData.isInitialized
                    findNavController().navigate(R.id.action_registerFragment_to_OTPFragment)

                }

                is NetworkResult.Error -> {
                    Toast.makeText(activity as LoginActivity, it.msg, Toast.LENGTH_SHORT).show()

                }

                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.transparentBg.isVisible = true
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == CAM_PERM_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contract.launch(imageUri)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Camera permission is require to click photo.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun createImageUri(): Uri {
        val image = File(requireContext().applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(
            requireContext().applicationContext,
            "com.zealicon_2024.fileProvider", image
        )
    }

    private fun askCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                CAM_PERM_CODE
            )
        } else {
            contract.launch(imageUri)
        }
    }

    private fun convertImageToBase64(image: ImageView): String {
        val drawable = image.drawable as BitmapDrawable
        return convertBitmapToBase64(drawable.bitmap)
    }

    private fun convertBitmapToBase64(bitmap: Bitmap): String {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        val image = stream.toByteArray()
        return Base64.encodeToString(image, Base64.DEFAULT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}