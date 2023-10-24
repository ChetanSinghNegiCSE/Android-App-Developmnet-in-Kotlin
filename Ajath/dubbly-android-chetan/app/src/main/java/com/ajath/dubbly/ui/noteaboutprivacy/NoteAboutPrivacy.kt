package com.ajath.dubbly.ui.noteaboutprivacy

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityNoteAboutPrivacyBinding
import com.ajath.dubbly.ui.privacypolicy.PrivacyPolicy
import kotlinx.coroutines.launch


class NoteAboutPrivacy : AppCompatActivity() {
    private var context = this@NoteAboutPrivacy
    private lateinit var binding: ActivityNoteAboutPrivacyBinding
    val TAG = "NoteAboutPrivacyLogs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_note_about_privacy)

        lifecycleScope.launch {
            getMyData()
        }
        binding.agreeButton.setOnClickListener {
            Toast.makeText(context, "Agreed", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.disagreeButton.setOnClickListener {
            val intent = Intent(this@NoteAboutPrivacy, PrivacyPolicy::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
            Toast.makeText(context, "Disagreed", Toast.LENGTH_SHORT).show()
        }
    }


    private fun getMyData() {
        /*val retrofitData=APIInterface.APIClient(context).apiInstance
          try{
              lifecycleScope.launch {
                  val response = retrofitData.getNotePrivacy().execute()
                  if(response.isSuccessful){
                      Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()
                      binding.txtnote.text = response.body()?.message.toString()

                  }else{
                      Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()
                  }
              }
          }catch (e:Exception){
              Log.d(TAG, "getMyData: ${e.message}")
          }*/
    }


}