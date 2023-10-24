package com.ajath.dubbly.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivitySignInBinding
import com.ajath.dubbly.ui.forgotpassword.ForgotPasswordActivity
import com.ajath.dubbly.ui.signup.SignUpActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch


const val RC_SIGN_IN = 123
class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private var context = this@SignInActivity
    private lateinit var signInViewModel: SignInViewModel
    private val TAG = "SignInLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_sign_in)
        binding.googleBtn.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile() // Add this line to request profile information
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        signInViewModel = ViewModelProvider(
            context,
            SignInVMFactory(context)
        )[SignInViewModel::class.java]

        initViews()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data!!)
            val account: GoogleSignInAccount? = result?.signInAccount
            handleSignInResult(account)
        }

    }

    private fun handleSignInResult(account: GoogleSignInAccount?) {
        if (account != null) {
            val displayName = account.displayName
            val email = account.email
            val photoUrl = account.photoUrl.toString()

            Log.d(TAG, "handleSignInResult: $displayName $email $photoUrl")
        }else{
            Log.d(TAG, "handleSignInResult: Account is Null")
        }
    }

    private fun initViews() {
        binding.signInBtn.setOnClickListener {
            validateViews()
        }

        binding.tvSignUp.setOnClickListener {
            signInViewModel.startNewActivity(SignUpActivity::class.java)
        }

        binding.forgotPasswordTV.setOnClickListener {
            signInViewModel.startNewActivity(ForgotPasswordActivity::class.java)
        }

    }

    private fun validateViews() {
        val password = binding.getPassword.text
        val email = binding.getEmail.text
        if (email?.isNotEmpty()!! && password?.isNotEmpty()!!) {
            lifecycleScope.launch {
                signInViewModel.validateSignIn(
                    email.toString(),
                    password.toString(),
                    binding.progressBar
                )
            }
        } else if (email.isEmpty()) {
            binding.getEmail.error = "Please Enter Your Email"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.getEmail.error = "Please Enter a Valid Email"
        } else if (password?.isEmpty()!!) {
            binding.getPassword.error = "Please Enter Your Password"
        } else if (password.length <= 4) {
            binding.getPassword.error = "Password cannot be shorter than 4 Alphabets"
        }
    }
}
