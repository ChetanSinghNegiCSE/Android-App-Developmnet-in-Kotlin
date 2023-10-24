package com.ajath.dubbly.ui.rateourapp

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import com.ajath.dubbly.R

class RateOurApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_our_app)
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        val showRatingButton = findViewById<Button>(R.id.bton)
        val backBtn = findViewById<ImageView>(R.id.backButton)

        showRatingButton.setOnClickListener {
            val rating = ratingBar.rating
            val ratingMessage = "Rating: $rating"
            showToast(ratingMessage)
        }
        backBtn.setOnClickListener {
            finish()
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        }

    }
