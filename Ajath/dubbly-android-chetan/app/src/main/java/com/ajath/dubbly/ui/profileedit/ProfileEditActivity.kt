package com.ajath.dubbly.ui.profileedit

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityProfileEditBinding
import com.ajath.dubbly.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


class ProfileEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileEditBinding
    private val context = this@ProfileEditActivity
    private lateinit var profileEditVM: ProfileEditVM
    private val TAG = "picLogs"
    lateinit var imageUri : Uri



    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
                imageUri = it!!
                binding.userIV.setImageURI(it)

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(context, R.layout.activity_profile_edit)

        profileEditVM = ViewModelProvider(
            context,
            ProfileEditVMFactory(applicationContext)
        )[ProfileEditVM::class.java]


        initViews()

        binding.saveBtn.setOnClickListener {
            validateViews()
            finish()

        }

        binding.bbtn.setOnClickListener {
            finish()
        }

        binding.cameraIV.setOnClickListener {

            openGallery()
        }
    }

    private fun openGallery() {
        resultLauncher.launch("image/*")
    }

    private fun initViews() {
        val nameD = intent.getStringExtra("name")
        val mobileD = intent.getStringExtra("phNum")
        val languageD = intent.getStringExtra("lang")
        val emailD = intent.getStringExtra("email")

        binding.nameET.text = Editable.Factory.getInstance().newEditable(nameD)
        binding.numberET.text = Editable.Factory.getInstance().newEditable(mobileD)
        binding.languageET.text = Editable.Factory.getInstance().newEditable(languageD)
        binding.emailTV.text = Editable.Factory.getInstance().newEditable(emailD)
    }

    private fun validateViews() {
        val name = binding.nameET.text.toString()
        val mobileNum = binding.numberET.text.toString()
        val language = binding.languageET.text.toString()

        if (name.isEmpty()) {
            binding.nameET.error = "Please Enter the Name"

        } else if (mobileNum.isEmpty()) {
            binding.numberET.error = "Please Enter Mobile Number"
        } else if (language.isEmpty()) {
            binding.languageET.error = "Please Enter Language"
        } else {
            lifecycleScope.launch {
                profileEditVM.validateProfileEdit(name, mobileNum, language)

                uploadImage()
            }
        }
    }

    private suspend fun uploadImage() {
        val filesDir = applicationContext.filesDir
            val file = File(filesDir, "image.jpg")

        val inputStream = contentResolver.openInputStream(imageUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)

        val requestBody = file.asRequestBody("image/png".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("files", file.name, requestBody)

        try {
            val apiClient = APIInterface.APIClient(this).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.uploadImage(part).execute()

            }
            Log.d(TAG, "upload: $response")
            if (response.isSuccessful) {
                Log.d(TAG, "upload: ${response.body()?.profile_pic}")
                Toast.makeText(context, "${response.body()?.profile_pic}", Toast.LENGTH_SHORT)
                    .show()
            } else if (response.code() == 401) {

                Toast.makeText(context, "Unauthorized", Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "HTTP Status Code: ${response.code()}")
                Log.d(TAG, "updateData: ${response.errorBody()}")
                Toast.makeText(context, " ${response.message()}", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }

    }
}

