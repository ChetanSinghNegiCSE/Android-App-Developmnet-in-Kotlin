package com.ajath.dubbly.ui.aboutuser

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AboutUserVMFactory(val context : Context) : ViewModelProvider.Factory  {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AboutUserViewModel(context) as T
    }
}