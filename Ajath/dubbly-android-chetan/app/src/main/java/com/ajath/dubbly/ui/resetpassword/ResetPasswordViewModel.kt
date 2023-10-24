package com.ajath.dubbly.ui.resetpassword

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajath.dubbly.network.APIInterface
import com.ajath.dubbly.ui.signin.SignInActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResetPasswordViewModel(val context : Context) : ViewModel() {

    val TAG = "ResetPasswordLogs"

    suspend fun validateResetPass(username: String, password: String, progressBar: ProgressBar, activity: Activity){
        viewModelScope.launch {
            fetchData(username, password, progressBar, activity)
        }
    }

    fun startNewActivity(newActivity : Class<*>) {
        val intent = Intent(context, newActivity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    private suspend fun fetchData(username: String, password: String, progressBar: ProgressBar, activity: Activity) {
        progressBar.visibility = View.VISIBLE
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {apiClient.getResetPassword(username, password).execute()}
            if (response.isSuccessful) {
                progressBar.visibility = View.GONE
                startNewActivity(SignInActivity::class.java)
                activity.finish()
                Log.d(TAG, "fetchData: ${response.body()}")
                Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            } else {
                progressBar.visibility = View.GONE
                Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            progressBar.visibility = View.GONE
            Log.d(TAG, "validateResetPass: ${e.message}")
        }
    }


}