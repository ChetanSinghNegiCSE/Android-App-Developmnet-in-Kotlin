package com.example.restapi.Model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restapi.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


class MainViewModel(val context: Context): ViewModel() {
    private val TAG = "HomeLog"
     val arrayList = mutableListOf<PostModel>()
    suspend fun fetchAllPosts() {

        try {
            val apiClient = ApiClient().apiInstance
            val response = withContext(Dispatchers.IO){
                apiClient.fetchAllPosts().execute()
            }

            if (response.isSuccessful) {
                arrayList.addAll(response.body()!!)
            } else {
                Log.d(TAG, "fetchAllPosts: ${response.errorBody()}")

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "fetchAllPosts: ${e.message}")
        }

    }

    suspend fun createPost(title: String , content : String ) {
         try {
            val apiClient = ApiClient().apiInstance
            val response = withContext(Dispatchers.IO){
                apiClient.getPost(title,content).execute()
            }
            if (response.isSuccessful) {
                val res = response.body()
                Log.d(TAG, "createPost: $res")
                Toast.makeText(context, "$res", Toast.LENGTH_LONG).show()
                } else {
                Log.d(TAG, "createPost: else block")
                }
        } catch (e: IOException) {
             Log.d(TAG, "createPost: ${e.message}")
        }
    }

    suspend fun deletePost (id : Int){

        try {
            val apiClient = ApiClient().apiInstance
            val response = withContext(Dispatchers.IO){
                apiClient.deletePost(id).execute()
            }
            if (response.isSuccessful) {
                val res = response.body()
                arrayList.removeAt(id)
                Toast.makeText(context, "${fetchAllPosts()}", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "createPost: $res")
            } else {
                Log.d(TAG, "createPost: else block")
            }
        } catch (e: IOException) {
            Log.d(TAG, "createPost: ${e.message}")
        }

    }
}

