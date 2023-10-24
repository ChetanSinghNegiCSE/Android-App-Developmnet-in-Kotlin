package com.ajath.dubbly.ui.profileedit

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajath.dubbly.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import android.content.ContentResolver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class ProfileEditVM(val context: Context) :ViewModel() {
    val TAG = "ProfileEditLogs"
    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> get() = _selectedImageUri

    fun setSelectedImageUri(uri: Uri) {
        _selectedImageUri.value = uri
    }

    suspend fun validateProfileEdit(name: String, mobileNum: String, language: String) {
        viewModelScope.launch { 
            updateData(name,mobileNum,language)
        }
    }

    private suspend fun updateData(name: String, mobileNum: String, language: String) {

        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO) {apiClient.getProfileEdit(name, mobileNum,language).execute()}

            if (response.isSuccessful){
                Log.d(TAG, "updateData: ${response.body()}")
                Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
            }else if(response.code() == 401){

                Toast.makeText(context, "Unauthorized", Toast.LENGTH_SHORT).show()
            }else{
                Log.e(TAG, "HTTP Status Code: ${response.code()}")



                Log.d(TAG, "updateData: ${response.errorBody()}")
                Toast.makeText(context, " ${response.message()}", Toast.LENGTH_SHORT).show()
            }

        }catch (e :Exception){
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()

        }
    }











}