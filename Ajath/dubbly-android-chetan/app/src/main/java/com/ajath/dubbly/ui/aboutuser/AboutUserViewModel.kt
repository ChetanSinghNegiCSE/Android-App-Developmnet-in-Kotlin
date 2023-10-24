package com.ajath.dubbly.ui.aboutuser

import android.R
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajath.dubbly.network.APIInterface
import com.ajath.dubbly.ui.upload_video.UploadVideoActivity
import com.ajath.dubbly.ui.videouploadfirst.VideoUploadActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AboutUserViewModel(val context : Context) : ViewModel() {
    val TAG = "SignUpLogs"
    var selectedValue: String = ""


    suspend fun validateAboutUser(name: String, role: String, ageGroup: String, countryCode: Int) {
        viewModelScope.launch {
            fetchData(name, role,ageGroup,countryCode)
        }
    }

    private suspend fun fetchData(name: String, email: String, mobile: String, password: Int) {
        try{
           val apiClient =APIInterface.APIClient(context).apiInstance
           val response = withContext(Dispatchers.IO){
              apiClient.getAboutUser(name,email,mobile,password).execute()
           }
            if (response.isSuccessful){
                Log.d(TAG, "fetchData: ${response.body()}")
                Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                startNewActivity(VideoUploadActivity::class.java)
            }else{
                Log.d(TAG, "fetchData: ${response.body()}")
                Toast.makeText(context,"Fail : ${response.body()?.message.toString()}", Toast.LENGTH_SHORT)
                    .show()
            }
        }catch (e :Exception){
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "validateSignUp: ${e.message}")

        }
    }

    fun startNewActivity(newActivity : Class<*>) {
        val intent = Intent(context, newActivity)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun setupSpinner(spinner: Spinner, array: Array<String>) {
        val arrayAdapter = ArrayAdapter(
            context, R.layout.simple_spinner_dropdown_item, array
        )
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                 selectedValue= parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedValue = parent?.selectedItem.toString()
            }
        }
    }
}