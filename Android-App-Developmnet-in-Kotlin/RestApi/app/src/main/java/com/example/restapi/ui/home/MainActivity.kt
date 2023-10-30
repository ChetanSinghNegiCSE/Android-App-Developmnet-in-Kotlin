package com.example.restapi.ui.home

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restapi.Model.MainViewModel
import com.example.restapi.Model.PostModel
import com.example.restapi.Model.ViewModelFactory
import com.example.restapi.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var homeViewModel: MainViewModel
    private val TAG = "arrayLog"

    /*
        val arrayList = arrayListOf<PostModel>()
    */
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeViewModel = ViewModelProvider(
            this@MainActivity,
            ViewModelFactory(this@MainActivity)
        )[MainViewModel::class.java]


        lifecycleScope.launch {
            val recyclerView = findViewById<RecyclerView>(R.id.rv_home)
            val dialogFAB = findViewById<FloatingActionButton>(R.id.dialogFAB)

            dialogFAB.setOnClickListener {
                showCreatePOstDialog()
            }
            homeViewModel.fetchAllPosts()

            val adapter = HomeAdapter(this@MainActivity, homeViewModel.arrayList)
            recyclerView.layoutManager =
                LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = adapter
        }
    }

    private fun showCreatePOstDialog() {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.create_post_dialog, null)
        dialog.setContentView(view)

        val title = dialog.findViewById<EditText>(R.id.et_title)
        val body = dialog.findViewById<EditText>(R.id.et_body)
        val button = dialog.findViewById<Button>(R.id.btn_submit)
        button.setOnClickListener {
            lifecycleScope.launch {
                homeViewModel.createPost(title.text.toString(), body.text.toString())
                dialog.hide()
            }
        }
        dialog.show()
    }
}