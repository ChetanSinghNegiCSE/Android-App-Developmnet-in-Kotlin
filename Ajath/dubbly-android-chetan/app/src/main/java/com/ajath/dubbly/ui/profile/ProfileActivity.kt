package com.ajath.dubbly.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityProfileBinding
import com.ajath.dubbly.ui.profileedit.ProfileEditActivity
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var profileViewModel: ProfileViewModel
    private var context = this@ProfileActivity
    private lateinit var binding : ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(context, R.layout.activity_profile)
        profileViewModel = ViewModelProvider(context,
            ProfileVMFactory(applicationContext)
        )[ProfileViewModel::class.java]


        binding.bbtn.setOnClickListener{
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        validateProfileView()
    }

    private fun validateProfileView(){
        val image = binding.userIV
        val name = binding.nameET
        val email = binding.emailET
        val phNum = binding.numberET
        val lang = binding.languageET
        val editBtn=binding.editBtn



        lifecycleScope.launch {
            profileViewModel.validateProfile(image,name,email,phNum,lang,editBtn,this@ProfileActivity)
       }

    }
}