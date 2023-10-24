package com.example.spdemo

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var nameET:EditText
    private lateinit var ageET :EditText
    private lateinit var nextBtn:Button
    private lateinit var sp :SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameET = findViewById(R.id.etName)
        ageET = findViewById(R.id.etAge)
        nextBtn = findViewById(R.id.btnNext)

        sp = getSharedPreferences("My_Sp", MODE_PRIVATE)
        editor=sp.edit()


    }

    override fun onPause() {
        super.onPause()
        val name=nameET.text.toString()
        val age= ageET.text.toString().toInt()

        editor.apply(){
            putString("sp_Name",name)
            putInt("sp_Age",age)
            commit()
        }

    }

    override fun onResume() {
        super.onResume()

        val name = sp.getString("sp_Name",null)
        val age=sp.getInt("sp_Age",0)

        nameET.setText(name)
        if(age != 0) {
            ageET.setText(age.toString())
        }
    }
}