package com.ajath.dubbly.ui.forgotpassword

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajath.dubbly.network.APIInterface
import com.ajath.dubbly.ui.otp.OtpActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordViewModel(val context: Context) : ViewModel() {
    val TAG = "ForgotPasswordLogs"
    fun validateForgetPassword(email: String) {
        viewModelScope.launch {
            fetchForgotData(email)
        }
    }

    private suspend fun fetchForgotData(email: String) {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.getForgotPassword(email).execute()
            }
            if (response.isSuccessful) {
                Log.d(TAG, "fetchData: success ${response.body()}")
                Toast.makeText(
                    context,
                    "Success :${response.body()?.message.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                buttonClicked(email)
            } else {
                Toast.makeText(
                    context,
                    "Error: ${response.body()?.message.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "fetchForgotData: ${response.body()}")
            }
        } catch (e: Exception) {
            Log.d(TAG, "Validate Forgot Password : ${e.message}")
        }
    }

    private fun buttonClicked(email: String) {
        val intent = Intent(context, OtpActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("email" , email)
        Log.d(TAG, "buttonClicked: $email")
        context.startActivity(intent)
    }
}