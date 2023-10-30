package com.example.restapi.network

import com.example.restapi.Model.PostModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("posts")
    fun fetchAllPosts() : Call<List<PostModel>>

    @FormUrlEncoded
    @POST("posts")
    fun getPost(
        @Field("title") title : String,
        @Field("content") content :String
    ) : Call<List<PostModel>>

    @POST("posts")
    fun createPost(@Body postModel: PostModel):Call<PostModel>
}