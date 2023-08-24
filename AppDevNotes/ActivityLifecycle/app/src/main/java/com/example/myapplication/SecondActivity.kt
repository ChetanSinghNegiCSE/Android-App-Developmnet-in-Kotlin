package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val tv_msg=findViewById<TextView>(R.id.tv2AMessage)
        val msg=intent.getStringExtra("MSG")
        tv_msg.setText("hello $msg You have claimed offer")


    }
}