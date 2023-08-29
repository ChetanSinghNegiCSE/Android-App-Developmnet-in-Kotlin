package com.example.oopdemo

import android.util.Log


//Function

/*function is a peace of code that is called by name
 it can be passed data to operate on
 and can optionally return data.
*/

/*Method  is a peace of code that is called by name
  associated(related) with an object
  method is also a function

  IN General Methods are functions that belong to a class

  but not all functions are methods*/

open class Car {
    var maxSpeed = 150
    open fun start(){
        Log.i("MyTag","Car is Starting.......")
        Log.i("MyTag","Car Max Speed is $maxSpeed")

    }


}