package com.ajath.dubbly.model

data class SignInResponse(
    val message: String,
    val error : String,
    val statusCode: Int,
    val access_token : String,
    val refresh_token : String
)
