package com.ajath.dubbly.ui.upload_video

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.AdapterViewBindingAdapter.OnItemSelected
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityUploadVideoBinding
import com.ajath.dubbly.ui.dashboard.DashboardActivity

class UploadVideoActivity : AppCompatActivity() {
    val translateSpinner = arrayOf("English(US)", "Urdu", "Hindi", "Spanish", "German")
    val translateSpinner1 = arrayOf("English(US)", "Urdu", "Hindi", "Spanish", "German")
    private var context = this@UploadVideoActivity
    private lateinit var binding: ActivityUploadVideoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_video)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_upload_video)

        initViews()

        val arrayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            translateSpinner
        )
        binding.translateSpinner.adapter = arrayAdapter
        binding.translateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    binding.translateCB.isChecked = true

                    Toast.makeText(applicationContext, "process", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

        binding.translateSpinner1.adapter = arrayAdapter
        binding.translateSpinner1.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    binding.dubbedCB.isChecked = true
                    Toast.makeText(applicationContext,"process",Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }

    }

    private fun initViews() {
        binding.uploadVideoBtn.setOnClickListener {
            val intent = Intent(this@UploadVideoActivity, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }
}
