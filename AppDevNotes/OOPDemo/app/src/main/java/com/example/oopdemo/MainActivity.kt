package com.example.oopdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val or var diff Reinitialization ?

       /* val car = Car()
        car.maxSpeed=200
        car.start()*/

        /*val driver = Driver("King",42)
        driver.displayDetails()*/

        val myCar = MyCar()
        myCar.maxSpeed=500
        myCar.start()
    }
}