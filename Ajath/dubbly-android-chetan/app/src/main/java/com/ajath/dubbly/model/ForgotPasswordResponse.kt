package com.ajath.dubbly.model

data class ForgotPasswordResponse (

    val message : String,
    val statusCode :Int,
    val email : String,
    val otp : Int,
    val creationtime : String,
    val id :Int

)