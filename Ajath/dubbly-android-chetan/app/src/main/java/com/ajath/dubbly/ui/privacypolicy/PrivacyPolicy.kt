package com.ajath.dubbly.ui.privacypolicy

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityPrivacyPolicyBinding
import com.ajath.dubbly.network.APIInterface
import com.ajath.dubbly.ui.noteaboutprivacy.NoteAboutPrivacy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrivacyPolicy : AppCompatActivity() {
    private var context = this@PrivacyPolicy
    private lateinit var binding: ActivityPrivacyPolicyBinding
    val TAG = "PrivacyPolicyLogs"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_privacy_policy)

        lifecycleScope.launch(Dispatchers.IO) {
            getMyData();
        }

        binding.nextBtn.setOnClickListener {
            val intent = Intent(this@PrivacyPolicy, NoteAboutPrivacy::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }


    fun getMyData() {
        try {
            val retrofitData = APIInterface.APIClient(context).apiInstance
            val response = retrofitData.getPrivacyPolicy().execute()
            if (response.isSuccessful) {
                Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                binding.txtpp.text = response.body()?.message.toString()
            } else {
                Toast.makeText(context, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.d(TAG, "getMyData: ${e.message}")
        }

    }
}