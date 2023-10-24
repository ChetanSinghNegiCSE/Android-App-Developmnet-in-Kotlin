package com.ajath.dubbly.network

import android.content.Context
import com.ajath.dubbly.model.AboutUserResponse
import com.ajath.dubbly.model.CountryCodeResponse
import com.ajath.dubbly.model.ForgotPasswordResponse
import com.ajath.dubbly.model.NoteAboutPrivcy
import com.ajath.dubbly.model.OTPResponse
import com.ajath.dubbly.model.PrivacyPolicyResponse
import com.ajath.dubbly.model.ProfileEditResponse
import com.ajath.dubbly.model.ProfilePicResponse
import com.ajath.dubbly.model.ProfileResponse
import com.ajath.dubbly.model.ResetPasswordResponse
import com.ajath.dubbly.model.SignInResponse
import com.ajath.dubbly.model.SignUpResponse
import com.ajath.dubbly.model.TCResponse
import com.ajath.dubbly.util.Constants
import com.ajath.dubbly.util.SharedPref
import okhttp3.Interceptor
import okhttp3.Interceptor.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import java.io.File


interface APIInterface {
    @POST("auth/sign-up")
    @FormUrlEncoded
    fun getSignUp(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("mobile") mobile: String,
        @Field("password") password: String
    ): Call<SignUpResponse>


    @Multipart
    @POST("users/photo")
     fun uploadImage(

        @Part image: MultipartBody.Part


    ):Call<ProfilePicResponse>

    @GET("auth/userdetails")
    fun getProfile(
    ): Call<ProfileResponse>
    @POST("auth/profile")
    @FormUrlEncoded
    fun getProfileEdit(
        //@Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("mobile") mobile: String,
        @Field("language") language : String
    ): Call<ProfileEditResponse>

    @GET("users/terms")
    fun getTermsData(): Call<TCResponse>


    @GET("users/noteprivacy")
    fun getNotePrivacy(): Call<NoteAboutPrivcy>


    @GET("/country")
    fun getCountryCode(
    ): Call<List<CountryCodeResponse>>



    @GET("users/privacypolicy")
    fun getPrivacyPolicy(): Call<PrivacyPolicyResponse>

    @POST("auth/sign-in")
    @FormUrlEncoded
    fun getSignIn(
        @Field("username") userName: String,
        @Field("password") password: String
    ): Call<SignInResponse>

    @POST("otp/forgot-password")
    @FormUrlEncoded
    fun getForgotPassword(
        @Field("email") email: String,
    ): Call<ForgotPasswordResponse>


    @POST("otp/password-reset")
    @FormUrlEncoded
    fun getResetPassword(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResetPasswordResponse>

    @POST("otp/validate-otp")
    @FormUrlEncoded
    fun getOTP(
        @Field("email") email: String,
        @Field("otp") otp: Int,
    ): Call<OTPResponse>

    @POST("auth/about")
    @FormUrlEncoded
    fun getAboutUser(
        @Field("name") email: String,
        @Field("role") role: String,
        @Field("agegroup") ageGroup:String,
        @Field("country") country: Int?,
    ): Call<AboutUserResponse>

    class APIClient(context: Context) {
        private val sharedPref = SharedPref(context)
        private fun getOkHttpClientWithBearerToken(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val originalRequest: Request = chain.request()
                    val newRequest: Request = originalRequest.newBuilder()
                        .header("Authorization", "Bearer ${sharedPref.refreshToken}")
                        .build()
                    chain.proceed(newRequest)
                })
                .build()
        }

        val apiInstance: APIInterface

        init {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(getOkHttpClientWithBearerToken())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiInstance = retrofit.create(APIInterface::class.java)
        }
    }

}