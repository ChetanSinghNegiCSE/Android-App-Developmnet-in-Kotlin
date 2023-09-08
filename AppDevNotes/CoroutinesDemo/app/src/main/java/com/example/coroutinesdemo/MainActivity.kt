package com.example.coroutinesdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/* We Should always implement long running tasks asynchronously in a separate thread
   to achieve that we use coroutines
 */

class MainActivity : AppCompatActivity() {
    private var count = 0
    private lateinit var tvDownload : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.tvCount)
        val countButton = findViewById<Button>(R.id.btnCount)
        val downloadButton = findViewById<Button>(R.id.btnDownload)
        tvDownload=findViewById(R.id.tvDownload)

        countButton.setOnClickListener {
            textView.text = count++.toString()
        }
        downloadButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                downloadUserData()

            }
        }
    }
//we can't directly  call a view component from background thread
    private suspend fun downloadUserData() {
        for (i in 1..200000) {
            Log.i("MyTag", "Downloading user $i in ${Thread.currentThread().name}")

            withContext(Dispatchers.Main){//Switching Threads

                tvDownload.text="Downloading user $i"

        }
    }


    }
}

