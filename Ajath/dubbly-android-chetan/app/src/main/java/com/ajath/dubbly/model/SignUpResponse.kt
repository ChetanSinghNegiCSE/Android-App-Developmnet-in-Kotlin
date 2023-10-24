package com.ajath.dubbly.model

data class SignUpResponse(
    val message:String,
    val error :String,
    val statusCode:Int,
    val email :String,
    val password :String,
    val mobile : String,
    val name : String,
    val country: Int,
    val maillist: Boolean,
    val stripeCustomerId: String,
    val id: Int,
    val role: String,
    val agegroup: String,
)
