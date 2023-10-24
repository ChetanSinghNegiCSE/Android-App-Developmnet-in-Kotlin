package com.ajath.dubbly.ui.signin

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SignUpValidatorTest{
    @Test
    fun inputIsValid(){
        val email = "japneet@gmail.com"
        val password = "Japnee"
        val result =  SignUpValidator.validateInputs(email, password)
            assertThat(result).isEqualTo(true)
    }
    @Test
    fun inputIsNotValid(){
        val email = "ajath@gmafil.com"
        val password = "123"
        val result =  SignUpValidator.validateInputs(email, password)
        assertThat(result).isEqualTo(false)
    }
}