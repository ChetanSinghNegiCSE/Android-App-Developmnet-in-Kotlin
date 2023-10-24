package com.ajath.dubbly.ui.signin

import android.util.Patterns

object SignUpValidator {
    fun validateInputs(email: String, password: String): Boolean {
        return !((email.isEmpty() || password.isEmpty() || (password.length <= 4)))
    }
}