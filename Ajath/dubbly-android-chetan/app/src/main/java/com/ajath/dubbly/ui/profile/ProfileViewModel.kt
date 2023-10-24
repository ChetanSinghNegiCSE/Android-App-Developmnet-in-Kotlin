package com.ajath.dubbly.ui.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ajath.dubbly.R
import com.ajath.dubbly.network.APIInterface
import com.ajath.dubbly.ui.profileedit.ProfileEditActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(val context: Context) : ViewModel(){
    val TAG="ProfileViewLogs"
    val countryCodeArray = mutableListOf<String>()

    fun validateProfile(
        image: ImageView,
        name: TextView,
        email: TextView,
        phNum: TextView,
        lang: TextView,
        editBtn: Button,
        activity: Activity
    ){
        viewModelScope.launch {
            fetchData(image,name,email,phNum,lang,editBtn,activity)
        }
    }

    private suspend fun fetchData(
        image: ImageView,
        name: TextView,
        email: TextView,
        phNum: TextView,
        lang: TextView,
        editBtn: Button,
        activity: Activity
    ) {
        try {
            val apiClient = APIInterface.APIClient(context).apiInstance
            val response = withContext(Dispatchers.IO){
                apiClient.getProfile().execute()
            }
            if (response.isSuccessful){
                name.text= response.body()?.name.toString()
                email.text = response.body()?.email.toString()
                phNum.text= response.body()?.mobile.toString()
                lang.text= response.body()?.language.toString()

                editBtn.setOnClickListener {
                    startNewActivity(name.text.toString(),
                        email.text.toString(),
                        phNum.text.toString(),
                        lang.text.toString(),
                        ProfileEditActivity::class.java)
                }
                Log.d(TAG, "fetchData: ${response.body()}")
            }else{
                Toast.makeText(context, "NotResponse ${response.body()}", Toast.LENGTH_LONG).show()
                Log.d(TAG, "fetchData: ${response.body()}")
            }
        }catch ( e : Exception){
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadImage(imageUrl: String, image: ImageView) {
//        Glide.with(context)
//            .load(imageUrl)
//            .placeholder(R.drawable.profile)  // Placeholder image while loading
//            .error(R.drawable.cancel)
//            .into(image)
    }



    private fun startNewActivity(
        name: String,
        email: String,
        phNum: String,
        lang: String,
        newActivity: Class<*>
    ){

        val intent =Intent(context,newActivity)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("phNum", phNum)
            intent.putExtra("lang", lang)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}