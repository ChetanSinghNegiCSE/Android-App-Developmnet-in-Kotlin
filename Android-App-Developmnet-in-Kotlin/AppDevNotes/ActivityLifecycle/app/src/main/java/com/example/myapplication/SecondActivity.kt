package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.i("MY TAG","SecondActivity : onCreate ")

        val tv_msg=findViewById<TextView>(R.id.tv2AMessage)
        val msg=intent.getStringExtra("MSG")
        tv_msg.setText("hello $msg You have claimed offer")


    }

    override fun onStart() {
        super.onStart()
        Log.i("MY TAG","SecondActivity : onStart ")
    }





    override fun onResume() {
        super.onResume()
        Log.i("MY TAG","SecondActivity : onResume ")

    }

    override fun onPause() {
        super.onPause()
        Log.i("MY TAG","SecondActivity : OnPause ")

    }

    override fun onStop() {
        super.onStop()
        Log.i("MY TAG","SecondActivity : onStop ")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MY TAG","SecondActivity : onDestroy ")

    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MY TAG","SecondActivity : onRestart ")

    }
}
