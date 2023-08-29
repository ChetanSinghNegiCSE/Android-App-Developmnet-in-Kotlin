package com.example.oopdemo

import android.util.Log

class Driver(var name : String, credit : Int) {

//    var driverName=""
//    lateinit var driverName : String
//    var driverName = name
    var totalCredit=50;
    val car = Car()

    init {
        totalCredit += credit
//        driverName=name
        car.maxSpeed=230
        car.start()
    }

    fun displayDetails(){
//        Log.i("MyTag","Driver Name is $driverName")
        Log.i("MyTag","Driver Name is $name and Total Credit is $totalCredit")

    }

}