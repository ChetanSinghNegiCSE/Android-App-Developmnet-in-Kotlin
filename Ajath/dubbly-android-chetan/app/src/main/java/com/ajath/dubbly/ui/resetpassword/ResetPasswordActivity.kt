package com.ajath.dubbly.ui.resetpassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityResetPasswordBinding
import kotlinx.coroutines.launch

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private var context = this@ResetPasswordActivity
    private lateinit var resetPasswordViewModel: ResetPasswordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_reset_password)

        resetPasswordViewModel = ViewModelProvider(
            context,
            ResetPasswordVFactory(applicationContext)
        )[ResetPasswordViewModel::class.java]
        initViews()
    }

    private fun initViews() {
        val email = intent.getStringExtra("email")
        val newPassword = binding.newPassword.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        binding.btnRP.setOnClickListener {
            if (confirmPassword != newPassword) {
                binding.newPassword.error = "Password Does not Match"
            } else {
                lifecycleScope.launch {
                    resetPasswordViewModel.validateResetPass(
                        email.toString(),
                        confirmPassword,
                        binding.progressBar,
                        context
                    )
                }
            }
        }

    }
}
