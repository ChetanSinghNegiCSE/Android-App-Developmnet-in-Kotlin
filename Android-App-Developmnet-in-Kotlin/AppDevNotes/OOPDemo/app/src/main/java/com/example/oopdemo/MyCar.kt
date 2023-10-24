package com.example.oopdemo

import android.util.Log


//Inheritance,Inheritance with interface
class MyCar : Car(),SpeedController {

    //Overriding Functions
    override fun start() {
        Log.i("MyTag","MyCar is Starting..... and Brand ID is ${getBrandID()} ")
    }

    override fun absFun1() {

    }

    override fun absFun2() {

    }
}