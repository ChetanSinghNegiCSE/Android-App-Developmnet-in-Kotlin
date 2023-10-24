package com.ajath.dubbly.ui.videoedit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityVideoEditBinding
import com.ajath.dubbly.ui.dashboard.DashboardActivity
import ly.img.android.pesdk.VideoEditorSettingsList
import ly.img.android.pesdk.backend.model.EditorSDKResult
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.backend.model.state.TrimSettings
import ly.img.android.pesdk.ui.activity.VideoEditorBuilder
import ly.img.android.pesdk.ui.model.state.UiConfigAudio
import java.util.concurrent.TimeUnit

class VideoEditActivity : AppCompatActivity() {
    val TAG = "VideoActivityLogs"

    companion object {
        private const val GALLERY_REQUEST_CODE = 0x69
        private const val EDITOR_REQUEST_CODE = 501
    }

    private lateinit var binding: ActivityVideoEditBinding
    private var context = this@VideoEditActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_video_edit)



        binding.videoView.setOnErrorListener { mp, what, extra ->
            Log.d(TAG, "onCreate: ${mp.toString()}")
            return@setOnErrorListener true
        }

        binding.galleryBtn.setOnClickListener {
   /*         val intent = Intent(context , DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()*/
            invoke()
        }
    }
         fun invoke() {
             val getVideo = intent.getStringExtra("VIDEO_PATH").toString()
             val videoUri = Uri.parse(getVideo)

             val selectedVideoUri: Uri? = videoUri

             if (selectedVideoUri != null) {
                 binding.videoView.setVideoURI(selectedVideoUri)
                 val mediaController = MediaController(context)
                 mediaController.setAnchorView(binding.videoView)
                 binding.videoView.setMediaController(mediaController)
                 // Set the video URI and start playback
                 binding.videoView.requestFocus()
                 binding.videoView.start()
             } else {
                 Toast.makeText(context, "Video Not valid", Toast.LENGTH_SHORT).show()
             }
            val settingsList = VideoEditorSettingsList(false)
                .configure<LoadSettings> {
                    it.source = selectedVideoUri
                }
             settingsList.configure<TrimSettings> {
                it.setMinimumVideoLength(2, TimeUnit.SECONDS)
                it.setMaximumVideoLength(5, TimeUnit.SECONDS)
                it.forceTrimMode = TrimSettings.ForceTrim.IF_NEEDED
            }
            VideoEditorBuilder(context)
                .setSettingsList(settingsList)
                .startActivityForResult(context, EDITOR_REQUEST_CODE)
            settingsList.release()
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        intent ?: return
        when (requestCode) {
            EDITOR_REQUEST_CODE -> {
                val result = EditorSDKResult(intent)
                when (result.resultStatus) {
                    EditorSDKResult.Status.CANCELED -> showMessage("Editor cancelled")
                    EditorSDKResult.Status.EXPORT_DONE -> showMessage("Result saved at ${result.resultUri}")
                    else -> {
                        showMessage("Error")
                    }
                }
            }

            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    intent
                        .data?.let {
                            showEditor(it)
                        } ?: showMessage("Invalid Uri")
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    showMessage("User cancelled selection")
                }
            }
        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        intent ?: return
        if (requestCode == EDITOR_REQUEST_CODE) {
            // Wrap the intent into an EditorSDKResult
            val result = EditorSDKResult(intent)
            when (result.resultStatus) {
                EditorSDKResult.Status.CANCELED -> showMessage("Editor cancelled")
                EditorSDKResult.Status.EXPORT_DONE -> showMessage("Result saved at ${result.resultUri}")
                else -> {
                }
            }
        }
    }*/

    private fun showMessage(s: String) {
        Toast.makeText(context, "$s", Toast.LENGTH_SHORT).show()
    }


    private fun showEditor(uri: Uri) {
        try {
            val settingsList = VideoEditorSettingsList(false)
                .configure<LoadSettings> {
                    it.source = uri
                }
            // Other code here
            settingsList.configure<UiConfigAudio> {
                // Set the audio track list using the ids defined in the AudioTrackAssets above

                // Start the video editor using VideoEditorBuilder
                // The result will be obtained in onActivityResult() corresponding to EDITOR_REQUEST_CODE
                VideoEditorBuilder(context)
                    .setSettingsList(settingsList)
                    .startActivityForResult(context, EDITOR_REQUEST_CODE)
                // Release the SettingsList once done
                settingsList.release()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d(TAG, "showEditor: ${e.message}")
        }
    }
}
