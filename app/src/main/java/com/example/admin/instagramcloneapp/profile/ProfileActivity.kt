package com.example.admin.instagramcloneapp.profile

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.View
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.layout_bottom_navigation_view.*
import kotlinx.android.synthetic.main.snippet_profile_toolbar.*

class ProfileActivity: AppCompatActivity() {
    private val TAG = ProfileActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Log.d(TAG,"Oncreated")

        setupBottomNavigationView()
        setupToolbar()
        progressBar.visibility = View.GONE
        profileMenu.setOnClickListener {
            startActivity(Intent(this, AccountSettingActivity::class.java))
        }
    }

    private val ACTIVITY_NUM: Int = 4

    /**
     * setup Toolbar
     */
    private fun setupToolbar() {
        setSupportActionBar(profileToolBar)
        profileToolBar.setOnMenuItemClickListener {
            Log.d(TAG, "OnMenuItemClicked: ${it.toString()}")
            when(it.itemId) {
                R.id.profileMenu -> {
                    Log.d(TAG, "OnMenuItemClicked: Navigating ")
                }
            }
            false
        }
    }

    /**
     * Setup Bottom View Pager
     */
    private fun setupBottomNavigationView(){
        Log.d(TAG,"Setup Bottom Navigation View")
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavViewBar)
        BottomNavigationViewHelper.enableNaviagation(this, bottomNavViewBar)
        val menu = bottomNavViewBar.menu
        val menuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true
    }

}