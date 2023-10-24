package com.ajath.dubbly.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.ajath.dubbly.R
import com.ajath.dubbly.databinding.ActivityDashboardBinding
import com.ajath.dubbly.ui.TermsAndCondition.TermsAndCondition
import com.ajath.dubbly.ui.privacypolicy.PrivacyPolicy
import com.ajath.dubbly.ui.profile.ProfileActivity
import com.ajath.dubbly.ui.rateourapp.RateOurApp
import com.ajath.dubbly.ui.signin.SignInActivity
import com.ajath.dubbly.util.SharedPref

class DashboardActivity : AppCompatActivity() {
    private var context = this@DashboardActivity
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var sharedPref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(context, R.layout.activity_dashboard)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        sharedPref = SharedPref(context)
        binding.appBar.navBar.setOnClickListener {
            actionBarDrawerToggle =
                ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
            binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)
            actionBarDrawerToggle.syncState()
            binding.drawerLayout.openDrawer(GravityCompat.START) // Replace with the appropriate Gravity

            openSideMenuBar()
        }

        val headerView =
            binding.navView.getHeaderView(0) // 0 index for the first (and usually only) header
        val profileViewBtn = headerView.findViewById<TextView>(R.id.viewBtn)
        val closeNav = headerView.findViewById<ImageView>(R.id.closeNavBar)

        closeNav.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        profileViewBtn.setOnClickListener {
            val intent =Intent(this@DashboardActivity,ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }

    private fun openSideMenuBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.opthomes -> Toast.makeText(
                    context,
                    "coming soon",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.optlanguage -> Toast.makeText(
                    context,
                    "coming soon",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.optdraft -> Toast.makeText(
                    context,
                    "coming soon",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.opthistory -> Toast.makeText(
                    context,
                    "coming soon",
                    Toast.LENGTH_SHORT
                ).show()

                R.id.optrateapp -> startActivity(RateOurApp::class.java)

                R.id.opttac -> startActivity(TermsAndCondition::class.java)

                R.id.optprivacy -> startActivity(PrivacyPolicy::class.java)

                R.id.optlogout -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton(
                            "Yes"
                        ) { _, _ ->
                            run {
                                sharedPref.login = "no"
                                val intent = Intent(context, SignInActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                                startActivity(intent)
                                finish()
                                Toast.makeText(
                                    context,
                                    "Logout SuccessFull",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        .setNegativeButton(
                            "No"
                        ) { dialog, _ -> dialog.cancel() }
                    val alert = builder.create()
                    alert.show()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            AlertDialog.Builder(context)
                .setTitle("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes") { _, _ -> finish() }
                .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                .show()
        }
    }

    fun startActivity(newActivity : Class<*>){
        val intent = Intent(context, newActivity)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)

    }
}