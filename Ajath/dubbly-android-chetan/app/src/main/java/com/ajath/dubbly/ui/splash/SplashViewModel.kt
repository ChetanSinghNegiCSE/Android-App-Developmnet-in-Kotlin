package com.ajath.dubbly.ui.splash

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.ajath.dubbly.ui.dashboard.DashboardActivity
import com.ajath.dubbly.ui.signin.SignInActivity
import com.ajath.dubbly.util.SharedPref

class SplashViewModel(val context : Context): ViewModel() {
    private val sharedPref = SharedPref(context)

    fun startActivity() {
        if(sharedPref.login == "no"){
            val intent = Intent(context, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            context.startActivity(intent)
        }else{
            val intent = Intent(context, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            context.startActivity(intent)
        }

    }

}