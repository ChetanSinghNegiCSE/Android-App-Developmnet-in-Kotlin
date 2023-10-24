package com.example.apiapp.network

import com.example.apiapp.model.UserResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface APIInterface {
    @GET("/users")
    fun getUser() : Call<List<UserResponse>>

}