package com.example.retrofit

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.gson.JsonObject
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var apiService: ApiService
    private  var progressDialog: ProgressDialog? = null
    private lateinit var  userId : TextView
    private lateinit var  userName : TextView
    private lateinit var  userJob : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userId = findViewById(R.id.userId)
        userName = findViewById(R.id.userName)
        userId = findViewById(R.id.userId)


        apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        findViewById<Button>(R.id.btnGet).setOnClickListener {
            getUser()
        }

        findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            updateUser()
        }

        findViewById<Button>(R.id.btnDelete).setOnClickListener {
            deleteUser()
        }

        findViewById<Button>(R.id.btnCreate).setOnClickListener {
            createUser()
        }
    }

    private fun createUser() {
        lifecycleScope.launch {
            showLoading("Loading....")
            try {


                val body = JsonObject().apply {
                    addProperty("name","New User")
                    addProperty("job","Test")
                }



                val result = apiService.createUser( body)
                if (result.isSuccessful) {
                    userId.text= result.body()?.id.toString()
                    userName.text= result.body()?.name
                    Toast.makeText(this@MainActivity, "${result.body()}", Toast.LENGTH_SHORT).show( )
                }else {
                    Toast.makeText(this@MainActivity, "${result.errorBody()}", Toast.LENGTH_LONG)
                        .show()

                }

            }catch (e : Exception){
                Toast.makeText(this@MainActivity, "$e", Toast.LENGTH_LONG).show()

            }
            progressDialog?.dismiss()

        }

    }

    private fun deleteUser() {
        lifecycleScope.launch {
            showLoading("Deleting....")
            try {
                val result = apiService.deleteUser("2")

                if(result.isSuccessful){
                    userId.text= result.body().toString()
                    userName.text=result.body().toString()
                    Toast.makeText(this@MainActivity, "${result.body()}", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@MainActivity, result.message(), Toast.LENGTH_SHORT).show()
                }
            }catch (e:Exception){

                Toast.makeText(this@MainActivity, "${e.message}", Toast.LENGTH_SHORT).show()
            }
            progressDialog?.dismiss()

        }

    }

    private fun updateUser() {
        lifecycleScope.launch {
            showLoading("Loading....")
            try {


                val body = JsonObject().apply {
                    addProperty("name","Chetan")
                    addProperty("job","android")
                }



                val result = apiService.updateUser("2", body)
                if (result.isSuccessful) {
                    Toast.makeText(this@MainActivity, "${result.body()}", Toast.LENGTH_SHORT).show()

                    userName.text= result.body()?.name.toString()
                    progressDialog?.dismiss()

                } else {
                    Toast.makeText(this@MainActivity, "${result.errorBody()}", Toast.LENGTH_LONG)
                        .show()

                }
                progressDialog?.dismiss()

            }catch (e : Exception){
                Toast.makeText(this@MainActivity, "$e", Toast.LENGTH_LONG).show()
                progressDialog?.dismiss()

            }
            progressDialog?.dismiss()

        }
        progressDialog?.dismiss()

    }

    private fun getUser() {
        lifecycleScope.launch {
            showLoading("Loading....")
            try {


                val result = apiService.getUserByID("2")
            if (result.isSuccessful){
                userId.text= result.body()?.data?.id.toString()
                userName.text=result.body()?.data?.firsName.toString()
                Toast.makeText(this@MainActivity, "${result.body()?.data}", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this@MainActivity, "${result.errorBody()}", Toast.LENGTH_LONG).show()

            }
            progressDialog?.dismiss()
        }catch (e : Exception){
                Toast.makeText(this@MainActivity, "$e", Toast.LENGTH_LONG).show()

            }
            progressDialog?.dismiss()

        }
    }

    private fun showLoading(msg : String){
        progressDialog = ProgressDialog.show(this,null,msg,true)
    }
}