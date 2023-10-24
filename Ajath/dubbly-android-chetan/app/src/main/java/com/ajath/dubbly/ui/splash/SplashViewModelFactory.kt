package com.ajath.dubbly.ui.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajath.dubbly.ui.signin.SignInViewModel

class SplashViewModelFactory(val context : Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(context) as T
    }
}