package com.example.restapi.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASE_URL = "http://jsonplaceholder.org/"

class ApiClient{

    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(100,TimeUnit.SECONDS)
        .connectTimeout(100,TimeUnit.SECONDS)
        .build()

    val apiInstance : ApiInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiInstance = retrofit.create(ApiInterface::class.java)

    }


}