package com.example.oopdemo

import android.util.Log


//Inheritance
class MyCar : Car() {

    //Overriding Functions
    override fun start() {
        Log.i("MyTag","MyCar is Starting.....")
    }
}