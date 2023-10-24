package com.ajath.dubbly.ui.signin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajath.dubbly.network.APIInterface
import com.ajath.dubbly.ui.aboutuser.AboutUserActivity
import com.ajath.dubbly.util.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(val context: Context) : ViewModel() {
    val TAG = "SignInLogs"
    private val videoPermissionId =14
    private val sharedPref = SharedPref(context)
    private val videoPermissionNameList = if(Build.VERSION.SDK_INT >=33){
        arrayListOf(
            android.Manifest.permission.READ_MEDIA_VIDEO
        )
    }else{
        arrayListOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

     fun checkMultiplePermission(): Boolean {
        val listPermissionNeeded = arrayListOf<String>()
        for (permission in videoPermissionNameList) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                listPermissionNeeded.add(permission)
            }
        }
        if (listPermissionNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(

                context as Activity,
                listPermissionNeeded.toTypedArray(),
                videoPermissionId
            )
            return false
        }
        return true
    }

    suspend fun validateSignIn(
        username: String,
        password: String,
        progressBar: ProgressBar
    ){
        viewModelScope.launch {
            fetchData(username, password, progressBar)
        }
    }

    fun startNewActivity(newActivity : Class<*>) {
        val intent = Intent(context, newActivity)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        context.startActivity(intent)
    }

    private suspend fun fetchData(
        username: String,
        password: String,
        progressBar: ProgressBar,
    ) {
        progressBar.visibility = View.VISIBLE
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {apiClient.getSignIn(username, password).execute()}
            if (response.isSuccessful) {
                progressBar.visibility = View.GONE
                sharedPref.refreshToken = response.body()?.refresh_token.toString()
                sharedPref.login = "Yes"
                startNewActivity(AboutUserActivity::class.java)
                Log.d(TAG, "fetchData: ${response.body()} ${APIInterface.APIClient(context).apiInstance}")
                Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Log.e(TAG, "HTTP Status Code: ${response.code()}")

                progressBar.visibility = View.GONE
                Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT)
                    .show()
                Log.d(TAG, "validateSignIn Response not Successful: ${ response.body()}")
            }
        } catch (e: Exception) {
            progressBar.visibility = View.GONE
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "validateSignIn: ${e.message}")
        }
    }
}