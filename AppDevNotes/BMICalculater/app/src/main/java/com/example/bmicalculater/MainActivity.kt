package com.example.bmicalculater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var etWeight :EditText
    private lateinit var etHeight :EditText
    private lateinit var btnCalculate :Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etHeight=findViewById(R.id.etHeight)
        etWeight=findViewById(R.id.etWeight)
        btnCalculate=findViewById(R.id.btnCalculate)



        btnCalculate.setOnClickListener {

            val weight = etWeight.text.toString()
            val height = etHeight.text.toString()

            if(validateData(weight,height)) {

                val bmi = weight.toFloat() / ((height.toFloat() / 100) * ( height.toFloat() / 100))
                val bmi2Dig = String.format("%.2f", bmi).toFloat()

                Log.i("MyTag", bmi2Dig.toString())


                displayBmi(bmi2Dig)
            }
        }



    }

    private fun validateData(weight: String?, height: String?): Boolean {

       return when{
           weight.isNullOrEmpty() ->{
               Toast.makeText(this, "Weight is Empty", Toast.LENGTH_SHORT).show()
                return false
           }
           height.isNullOrEmpty() ->{
               Toast.makeText(this, "Height is Empty", Toast.LENGTH_SHORT).show()
                return false
           }


           else -> {
               return true
           }
       }

    }

    private fun displayBmi(bmi2Dig: Float) {
        val tvBmi=findViewById<TextView>(R.id.tvBmi)
        val bmiDis = findViewById<TextView>(R.id.tvMessage)
        val normalText = findViewById<TextView>(R.id.tvNormal)

        tvBmi.text=bmi2Dig.toString()
        normalText.text = "(Normal range is 18.5 - 24.9)"

        var resultText = ""
        var resultColor = 0


        when{

            bmi2Dig < 18.5 ->{
                resultText="Under Weight"
                resultColor=R.color.underWeight
            }

            bmi2Dig in 18.5 .. 24.9 ->{
                resultText="Healthy"
                resultColor=R.color.normal
            }
            bmi2Dig in 25.00 .. 29.99 ->{
                resultText="Over Weight"
                resultColor=R.color.overWeight
            }

            bmi2Dig > 29.99 ->{
                resultText="Obese"
                resultColor=R.color.obese
            }


        }

        bmiDis.text = resultText
        bmiDis.setTextColor(ContextCompat.getColor(this,resultColor))

    }
}