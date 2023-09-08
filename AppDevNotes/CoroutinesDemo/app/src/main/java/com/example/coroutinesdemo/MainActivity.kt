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

/* Always recommended start coroutines using main thread and then switch to background threads
   To launch coroutines in the main thread we use Dispatchers.Main.

   also have  => Dispatchers.Main       *-> coroutine will run in the main(UI) thread.only used for small lightweight task.
                                           Like - go to a UI function , go to a suspending function,Updates from Live Data.

                 Dispatchers.IO         *-> coroutine will run in the Background thread.
                                           from a shared pool of a on-demand created threads.
                                           used for work with local Data Base, Communicate with , work with Network and files.

                 Dispatchers.Default    -> Used for CPU Intensive tasks such as sorting a large list,
                                           Passing huge  JSON file.

                 Dispatchers.Unconfined -> Used in Global Scope .
                                           Coroutine will run on the current thread,but if it's suspended and resumed
                                           it will run on suspending function's thread.(Not recommended to use for android developers)

                  we can also make custom Dispatchers , like room and retrofit.

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
            /*CoroutineScope -> keep tracks ,cancels , handle the errors and exceptions of coroutines,
              it's a interface .
             */
            /* we have another scope interface called Global Scope
                used to launch top level coroutines which are operating one the whole application lifetime.
             */
                            //context
            CoroutineScope(Dispatchers.IO).launch{
                downloadUserData()

            }
            /*launch is a coroutine builder used to launch new coroutines
              4 main  coroutine builders=>

                launch     ->launches new coroutines without blocking current thread.
                async      ->Allow us to Launch coroutines in parallel , also launches new coroutines without blocking current thread.
                produce    ->Produce builder is for coroutines which produce a stream of elements.
                runBlocking->used for testing , coroutines created by using this builder will block the thread until coroutine is executing.


             */

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

