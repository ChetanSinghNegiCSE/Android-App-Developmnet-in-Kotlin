package com.ajath.dubbly.ui.TermsAndCondition

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityTermsandconditionBinding
import com.ajath.dubbly.model.TCResponse
import com.ajath.dubbly.network.APIInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsAndCondition : AppCompatActivity() {
    private var context = this@TermsAndCondition
    private  lateinit var binding: ActivityTermsandconditionBinding
    val TAG = "TermsAndConditionsLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_termsandcondition)
        getMyData()
        binding.cancelButton.setOnClickListener{
            Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show()
        }
        binding.accecptBtn.setOnClickListener {
            Toast.makeText(context, "Accepted", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.imageView8.setOnClickListener {
            finish()
        }
    }

    private fun getMyData() {
        val retrofitData = APIInterface.APIClient(context).apiInstance
        retrofitData.getTermsData().enqueue(object : Callback<TCResponse?> {
            override fun onResponse(call: Call<TCResponse?>, response: Response<TCResponse?>) {
                val responseBody = response.body()
                binding.tctxtview.text = responseBody?.message.toString()
                Log.d(TAG, "onResponse: ${responseBody.toString()}")
            }

            override fun onFailure(call: Call<TCResponse?>, t: Throwable) {
                Log.d(TAG,"onFailure:"+t.message)
            }
        })
    }
}