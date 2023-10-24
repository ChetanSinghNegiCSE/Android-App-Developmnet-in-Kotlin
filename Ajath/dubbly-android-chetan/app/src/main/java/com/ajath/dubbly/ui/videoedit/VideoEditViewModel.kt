package com.ajath.dubbly.ui.videoedit

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.util.LongSparseArray
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException
import ly.img.android.pesdk.VideoEditorSettingsList
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.ui.activity.VideoEditorBuilder
import java.lang.Math.ceil
import java.util.Formatter

class VideoEditViewModel(val context : Context, val activity: Activity) : ViewModel() {
    val TAG = "VideoEditLogs"
    companion object {
        private const val GALLERY_REQUEST_CODE = 0x69
        private const val EDITOR_REQUEST_CODE = 501
    }

    fun invoke() {
        val videoUri = activity.intent.getStringExtra("VIDEO_PATH")
        // In this example, we do not need access to the Uri(s) after the editor is closed
        // so we pass false in the constructor
        val settingsList = VideoEditorSettingsList(true)
            // Set the source as the Uri of the video to be loaded
            .configure<LoadSettings> {
                it.source = Uri.parse(videoUri)
            }
        VideoEditorBuilder(activity)
            .setSettingsList(settingsList)
            .startActivityForResult(activity, EDITOR_REQUEST_CODE)

        // Release the SettingsList once done
        settingsList.release()
    }
}


