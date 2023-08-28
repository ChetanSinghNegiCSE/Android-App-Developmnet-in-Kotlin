package com.example.myapplication

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private  var  msg=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("MY TAG","MainActivity : onCreate")

        val tv_hello=findViewById<TextView>(R.id.tvHello)
        val et_name = findViewById<EditText>(R.id.etName)
        val btn_submit = findViewById<Button>(R.id.btnSubmit)
        val btn_offer=findViewById<Button>(R.id.btnOffers)



        btn_submit.setOnClickListener {
            msg= et_name.text.toString()

            if(msg == ""){


                Toast.makeText(this, "Please Enter Your Name", Toast.LENGTH_SHORT).show()
                tv_hello.text=""
                btn_offer.visibility=GONE

            }else {

                tv_hello.text = "Hello $msg"
                et_name.text.clear()
                btn_offer.visibility=VISIBLE


            }
        }

        btn_offer.setOnClickListener {
            val intent = Intent(this,SecondActivity::class.java)
            intent.putExtra("MSG",msg)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        Log.i("MY TAG","MainActivity : onStart ")
    }





    override fun onResume() {
        super.onResume()
        Log.i("MY TAG","MainActivity : onResume ")

    }

    override fun onPause() {
        super.onPause()
        Log.i("MY TAG","MainActivity : OnPause ")

    }

    override fun onStop() {
        super.onStop()
        Log.i("MY TAG","MainActivity : onStop ")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("MY TAG","MainActivity : onDestroy ")

    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MY TAG","MainActivity : onRestart ")

    }
}