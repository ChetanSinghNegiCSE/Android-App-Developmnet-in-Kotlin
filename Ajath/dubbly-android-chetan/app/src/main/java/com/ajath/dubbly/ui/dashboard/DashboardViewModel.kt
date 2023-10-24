package com.ajath.dubbly.ui.dashboard

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.ajath.dubbly.R

class DashboardViewModel(val context: Context) : ViewModel() {
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}