



package com.example.clapapp
//Media Player API
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSeekBar
import com.google.android.material.floatingactionbutton.FloatingActionButton




class MainActivity : AppCompatActivity() {
    private  var mediaPlayer: MediaPlayer? = null
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var sbMedia: SeekBar
    private lateinit var tvStart:TextView
    private lateinit var tvEnd :TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val btnPlay = findViewById<FloatingActionButton>(R.id.fbPlay)
        val btnPause = findViewById<FloatingActionButton>(R.id.fbPause)
        val btnStop = findViewById<FloatingActionButton>(R.id.fbStop)

         sbMedia = findViewById(R.id.sbMedia)
         tvStart = findViewById(R.id.tvStart)
         tvEnd = findViewById(R.id.tvEnd)

        handler = Handler(Looper.getMainLooper())

        btnPlay.setOnClickListener {

            if(mediaPlayer == null){
                mediaPlayer=MediaPlayer.create(this,R.raw.crowd_cheer)
                initializeSeekbar()

            }
            mediaPlayer?.start()
        }

        btnPause.setOnClickListener {
            mediaPlayer?.pause()

        }

        btnStop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            mediaPlayer=null
            handler.removeCallbacks(runnable)
            sbMedia.progress =0

        }

    }

    private fun initializeSeekbar() {
            sbMedia.setOnSeekBarChangeListener(object :	SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar : SeekBar?, progress: Int, fromUser: Boolean) {
                    if(fromUser) mediaPlayer?.seekTo(progress)
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            })

        sbMedia.max = mediaPlayer!!.duration
        runnable = Runnable {
            sbMedia.progress = mediaPlayer!!.currentPosition
            handler.postDelayed(runnable,1000)
            val startTime = mediaPlayer!!.currentPosition/1000
            val duration = mediaPlayer!!.duration/1000
            val endTime = duration - startTime

            tvStart.text="$startTime Sec"
            tvEnd.text = "$endTime Sec"
        }
        handler.postDelayed(runnable,1000)
    }
}