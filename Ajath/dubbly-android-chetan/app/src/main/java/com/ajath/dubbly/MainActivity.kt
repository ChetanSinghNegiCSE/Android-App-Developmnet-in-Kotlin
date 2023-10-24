package com.ajath.dubbly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ajath.dubbly.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var context = this@MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}