package com.ajath.dubbly.ui.aboutuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityAboutUserBinding
import kotlinx.coroutines.launch

class AboutUserActivity : AppCompatActivity() {
    private var context = this@AboutUserActivity
    private var scSpinner = arrayOf("admin", "editor", "ghost")
    private var roleArray = arrayOf("admin", "editor", "ghost")
    private lateinit var binding: ActivityAboutUserBinding
    private lateinit var aboutUserVM: AboutUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_about_user)

        aboutUserVM = ViewModelProvider(
            context,
            AboutUserVMFactory(applicationContext)
        )[AboutUserViewModel::class.java]

        initViews()

    }

    private fun initViews() {
        aboutUserVM.setupSpinner(binding.ageSpinner,roleArray)
        aboutUserVM.setupSpinner(binding.roleSpinner,roleArray)
        aboutUserVM.setupSpinner(binding.countrySpinner,roleArray)

        binding.btnb.setOnClickListener {
            finish()
        }
        binding.btnb.setOnClickListener {
            finish()
        }
        binding.createAccBtn.setOnClickListener {
            validateViews()
        }
    }

    private fun validateViews() {
        val name = binding.nameET.text
        if (name?.isNotEmpty()!!) {
            lifecycleScope.launch {
                aboutUserVM.validateAboutUser(
                    name.toString(),
                    aboutUserVM.selectedValue,
                    aboutUserVM.selectedValue,
                    12
                )
            }
        }
    }
}