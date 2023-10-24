package com.ajath.dubbly.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivitySignUpBinding
import com.ajath.dubbly.ui.signin.SignInActivity
import com.ajath.dubbly.util.Validate
import kotlinx.coroutines.launch
import okhttp3.internal.notify

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private  var TAG = "SignUpLogs"
    private val context = this@SignUpActivity
    private lateinit var signUpViewModel: SignUpViewModel
    private var getCountryCOde = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_sign_up)

        signUpViewModel = ViewModelProvider(
            context,
            SignUpVMFactory(applicationContext)
        )[SignUpViewModel::class.java]

        initViews()
    }

    private fun initViews() {
        binding.btnSignUp.setOnClickListener {
            validateViews()
        }
        lifecycleScope.launch {
        signUpViewModel.fetchCountryCode()
        val arrayAdapter = ArrayAdapter<String>(
            context,
            R.layout.custom_spinner,
            signUpViewModel.countryCodeArray
        )
        binding.countryCodeACTV.setAdapter(arrayAdapter)
        binding.countryCodeACTV.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                    if(p0?.selectedItem.)
                    getCountryCOde = p0?.selectedItem.toString()
                    Log.d(TAG, "onItemSelected: $getCountryCOde")
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    getCountryCOde = p0?.selectedItem.toString()
                    Log.d(TAG, "onNothingSelected: $getCountryCOde")
                }
            }
        }

        binding.signinTV.setOnClickListener {
            signUpViewModel.startNewActivity(SignInActivity::class.java)
            finish()
        }
        binding.linearLayout.setOnClickListener{
            Toast.makeText(context, "Coming Soon!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateViews() {
        val firstN = binding.etFName.text.toString()
        val lastN = binding.etLName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val mobileNum = binding.etMobile.text.toString()
        val name = "$firstN $lastN"
        val validate = Validate()

        if(firstN.isEmpty()){
            binding.etFName.error="Please Enter First Name"
        }else if(lastN.isEmpty()){
            binding.etLName.error="Please Enter Last Name"
        }else if(email.isEmpty()){
            binding.etEmail.error="Please Enter email"
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.error="Enter Valid Email Id"
        }else if(mobileNum.isEmpty() || mobileNum.length <= 9 ){
            binding.etMobile.error="Enter Valid Mobile Number"
        }else if(password.isEmpty() || password.length <= 8 || !validate.validatePassword(password)){
            binding.etPassword.error="Enter valid password(password should contain lower case , upper case,numbers and symbols)"
        }else{
            lifecycleScope.launch {
                Log.d(TAG, "validateViews: $name $email $mobileNum $password")
                signUpViewModel.validateSignUp(name, email, "$getCountryCOde$mobileNum", password, this@SignUpActivity)
            }
        }
    }
}
