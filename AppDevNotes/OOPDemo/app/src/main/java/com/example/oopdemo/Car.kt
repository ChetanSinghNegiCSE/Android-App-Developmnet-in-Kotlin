package com.example.oopdemo

import android.util.Log


//Function
open class Car {
    var maxSpeed = 150
    fun start(){
        Log.i("MyTag","Car is Starting.......")
        Log.i("MyTag","Car Max Speed is $maxSpeed")

    }
}