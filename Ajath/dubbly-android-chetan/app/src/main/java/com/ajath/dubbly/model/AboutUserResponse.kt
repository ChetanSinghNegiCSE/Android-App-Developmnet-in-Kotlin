package com.ajath.dubbly.model

data class AboutUserResponse(
    val message : String,
    val statusCode : Int,
    val name : String,
    val role : String,
    val agegroup : String,
)
