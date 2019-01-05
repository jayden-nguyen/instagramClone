package com.example.admin.instagramcloneapp.likes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.layout_bottom_navigation_view.*

class LikesActivity: AppCompatActivity() {
    private val TAG = LikesActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.d(TAG,"Oncreated")

        setupBottomNavigationView()
    }

    private val ACTIVITY_NUM: Int = 3

    private fun setupBottomNavigationView(){
        Log.d(TAG,"Setup Bottom Navigation View")
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavViewBar)
        BottomNavigationViewHelper.enableNaviagation(this, bottomNavViewBar)
        val menu = bottomNavViewBar.menu
        val menuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true
    }
}