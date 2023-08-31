package com.example.viewmodellivedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get


class MainActivity : AppCompatActivity() {
    private lateinit var tvCount :TextView
    private lateinit var btnCount : Button
    //private var count = 0
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCount = findViewById(R.id.tvCount)
        btnCount = findViewById(R.id.btnCount)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        //tvCount.text = count.toString()
        //tvCount.text = viewModel.count.toString()
        viewModel.count.observe(this){
            tvCount.text = it.toString()
        }

        btnCount.setOnClickListener {
            /*
            ++count
            tvCount.text=count.toString()
            */
            viewModel.updateCount()
           // tvCount.text=viewModel.count.toString()

        }
    }
}