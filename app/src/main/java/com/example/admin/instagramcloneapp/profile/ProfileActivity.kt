package com.example.admin.instagramcloneapp.profile

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Menu
import android.view.View
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.layout_bottom_navigation_view.*
import kotlinx.android.synthetic.main.layout_center_profile.*
import kotlinx.android.synthetic.main.snippet_profile_toolbar.*

class ProfileActivity: AppCompatActivity() {
    private val TAG = ProfileActivity::class.simpleName
    private var mAdapter = GridImageAdapter()
    private val mImageUrlList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        Log.d(TAG,"Oncreated")

        setupBottomNavigationView()
        setupToolbar()
        progressBar.visibility = View.GONE
        setUpImageListData()
        setUpImageListView()
        profileMenu.setOnClickListener {
            startActivity(Intent(this, AccountSettingActivity::class.java))
        }
    }

    private fun setUpImageListData(){
        mImageUrlList.add("https://www.w3schools.com/w3css/img_snowtops.jpg")
        mImageUrlList.add("https://www.w3schools.com/w3css/img_lights.jpg")
        mImageUrlList.add("https://www.w3schools.com/w3css/img_mountains.jpg")
        mImageUrlList.add("https://www.w3schools.com/w3css/img_nature.jpg")
        mAdapter.setImageUrlList(mImageUrlList)
    }

    private fun setUpImageListView() {
        gridImageView.apply {
            layoutManager = GridLayoutManager(applicationContext, 4)
            adapter = mAdapter
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