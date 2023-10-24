package com.example.apiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiapp.databinding.ActivityMainBinding
import com.example.apiapp.model.UserResponse
import com.example.apiapp.network.APIInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var context = this@MainActivity
    private lateinit var mainViewModel: MainViewModel
    private val TAG = "MainLogs"
    private val responseList = mutableListOf<UserResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        mainViewModel = ViewModelProvider(
            context,
            MainVMFactory(context)
        )[MainViewModel::class.java]

        fetchData()

    }

    fun fetchData() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val service = retrofit.create(APIInterface::class.java)


        val call = service.getUser()
        lifecycleScope.launch {


            val response = withContext(Dispatchers.IO) {
                call.execute()
            }



            try {
                if (response.isSuccessful) {
                    val uses = response.body()

                    Log.d(TAG, "fetchData: $uses")
                    uses?.get(0)?.let { responseList.addAll(uses) }

                    binding.recyclerView.adapter = UserAdapter(responseList)

                } else {
                    Toast.makeText(context, "${response.errorBody()}", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "fetchData: ${response.body()}")

                }

            } catch (e: Exception) {
                Log.d(TAG, "fetchData: ${e.message}")

                //Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()

            }

        }

    }

}