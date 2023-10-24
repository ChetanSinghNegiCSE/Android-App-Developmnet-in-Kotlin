package com.ajath.dubbly.ui.forgotpassword

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityForgotPasswordBinding
import com.ajath.dubbly.ui.otp.OtpActivity
import kotlinx.coroutines.launch

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var forgetPasswordViewModel : ForgotPasswordViewModel
    private  var context = this@ForgotPasswordActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            context,
            R.layout.activity_forgot_password
        )

        forgetPasswordViewModel = ViewModelProvider(context,
            ForgotPasswordVMFactory(applicationContext)
            )[ForgotPasswordViewModel::class.java]

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnContinue.setOnClickListener {
            validateViews()
        }

    }

    private fun validateViews() {
        val email = binding.etEmail.text.toString()

        if(email.isEmpty()){
            binding.etEmail.error="Please Enter email"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error="Enter Valid Email Id"
        }else{
            lifecycleScope.launch {
                forgetPasswordViewModel.validateForgetPassword(email)
            }
        }
    }


}