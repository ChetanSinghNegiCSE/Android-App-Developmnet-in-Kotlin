package com.ajath.dubbly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomNavigation : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navigation)

        val settingsBtn: Button = findViewById(R.id.settingsBtn)

        settingsBtn.setOnClickListener {
            val view: View = layoutInflater.inflate(R.layout.item_bottom_sheet,null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
    }
}