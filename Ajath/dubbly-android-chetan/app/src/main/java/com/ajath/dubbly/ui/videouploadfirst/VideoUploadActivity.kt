package com.ajath.dubbly.ui.videouploadfirst

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityVideoUploadBinding
import com.ajath.dubbly.ui.confirmvideo.ConfirmVideoActivity

class VideoUploadActivity  : AppCompatActivity() {
    private lateinit var videoUploadViewModel: VideoUploadViewModel
    private var context = this@VideoUploadActivity
    private lateinit var binding: ActivityVideoUploadBinding
    val TAG = "VideoUploadActivityLogs"

    private val pickVideo = registerForActivityResult(ActivityResultContracts.GetContent()){videoUri: Uri? ->
        videoUri?.let{ uri ->
            Log.d(TAG, "val:${uri.path.toString()} ")
            val intent =Intent(this@VideoUploadActivity, ConfirmVideoActivity::class.java).apply {
                putExtra("VIDEO_PATH",uri.toString())
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_upload)

        binding = DataBindingUtil.setContentView(context, R.layout.activity_video_upload)

        videoUploadViewModel = ViewModelProvider(
            context,
            VideoUploadVMFactory(applicationContext)
        )[VideoUploadViewModel::class.java]

        initViews()
    }

    private fun initViews() {
        binding.uploadLocalVideo.setOnClickListener {
            videoUploadViewModel.openVideoForGallery(pickVideo)
        }
    }
}