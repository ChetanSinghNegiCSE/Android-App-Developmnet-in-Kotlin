package com.ajath.dubbly.ui.videouploadfirst

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class VideoUploadViewModel(val context:Context ) : ViewModel() {
    private val videoPermissionId =14
    private val videoPermissionNameList = if(Build.VERSION.SDK_INT >=33){
        arrayListOf(
            android.Manifest.permission.READ_MEDIA_VIDEO
        )
    }else{
        arrayListOf(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    fun openVideoForGallery(pickVideo : ActivityResultLauncher<String>) {
        pickVideo.launch("video/*")
    }



}
