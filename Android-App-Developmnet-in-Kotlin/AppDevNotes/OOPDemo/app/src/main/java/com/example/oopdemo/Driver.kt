package com.example.oopdemo

import android.util.Log

/*constructor formal parameter  is  Declare  within class name
and  Field Variables also Declare wih the help of  var and val
 */

//Visibility Modifiers

class Driver(var name : String, credit : Int) {

//    var driverName=""
//    lateinit var driverName : String
//    var driverName = name
    private var totalCredit=50 //we should declare variables private
    private val car = Car()



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