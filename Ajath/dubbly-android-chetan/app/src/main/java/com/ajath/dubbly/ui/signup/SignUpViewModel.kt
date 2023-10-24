package com.ajath.dubbly.ui.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajath.dubbly.network.APIInterface
import com.ajath.dubbly.ui.signin.SignInActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpViewModel(val context: Context) : ViewModel() {
    val TAG = "SignUpLogs"
    val countryCodeArray = mutableListOf<String>()

    suspend fun validateSignUp(
        name: String,
        email: String,
        mobile: String,
        password: String,
        activity: Activity
    ) {
        viewModelScope.launch {
            fetchData(name, email, mobile, password, activity)
        }
    }

    private suspend fun fetchData(
        name: String,
        email: String,
        mobile: String,
        password: String,
        activity: Activity
    ) {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.getSignUp(name, email, mobile, password).execute()
            }
            if (response.isSuccessful) {
                Log.d(TAG, "fetchData: ${response.body()}")
                Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                startNewActivity(SignInActivity::class.java)
                activity.finish()
            } else if (response.body()?.statusCode == 409) {
                Log.d(TAG, "fetchData: ${response.body()?.message.toString()}")
            } else {
                Toast.makeText(context, "This Email Already Exist", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Exception ${e.message}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "validateSignUp: ${e.message}")

        }
    }

    suspend fun fetchCountryCode() {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {
                apiClient.getCountryCode().execute()
            }
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d(TAG, "fetchCountryCode: $responseBody")
                for (i in responseBody!!) {
                    countryCodeArray.addAll(listOf(i.dial_code))
                }
                Log.d(TAG, "fetchCountryCode: $countryCodeArray ${countryCodeArray.size}")
            }
        } catch (e: Exception) {
            Log.d(TAG, "fetchCountryCode: ${e.message}")
        }
    }

    fun startNewActivity(newActivity: Class<*>) {
        val intent = Intent(context, newActivity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}