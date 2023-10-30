package com.ajath.dubbly.model

import okhttp3.MultipartBody

data class ProfilePicResponse(
    val id : Int,
    val email : String,
    val name : String,
    val mobile : String,
    val password : String,
    val role : String,
    val agegroup : String,
    val language : String,
    val country : String,
    val maillist : String,
    val stripeCustomerId : String,
    val profile_pic : String
)