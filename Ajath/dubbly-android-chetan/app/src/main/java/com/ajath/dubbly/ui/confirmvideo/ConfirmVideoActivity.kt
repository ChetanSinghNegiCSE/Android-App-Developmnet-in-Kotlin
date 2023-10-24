package com.ajath.dubbly.ui.confirmvideo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityConfirmVideoBinding
import com.ajath.dubbly.ui.videoedit.VideoEditActivity

class ConfirmVideoActivity : AppCompatActivity() {
    private lateinit var bidning: ActivityConfirmVideoBinding
    val TAG = "ConfirmVideoActivityLogs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bidning = DataBindingUtil.setContentView(
            this@ConfirmVideoActivity,
            R.layout.activity_confirm_video
        )

        val videoPath = intent.getStringExtra("VIDEO_PATH")
        Log.d(TAG, "data:$videoPath ")

        bidning.etPastLink.text = videoPath
        bidning.btnProceed.setOnClickListener {
            val intent = Intent(this@ConfirmVideoActivity, VideoEditActivity::class.java)
            intent.putExtra("VIDEO_PATH", videoPath)
            startActivity(intent)
        }
    }
}