package com.example.admin.instagramcloneapp.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.admin.instagramcloneapp.*
import com.example.admin.instagramcloneapp.home.HomeActivity
import com.example.admin.instagramcloneapp.likes.LikesActivity
import com.example.admin.instagramcloneapp.profile.ProfileActivity
import com.example.admin.instagramcloneapp.search.SearchActivity
import com.example.admin.instagramcloneapp.share.ShareActivity
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx

class BottomNavigationViewHelper {
    companion object {
        private val TAG = BottomNavigationViewHelper::class.simpleName

        @JvmStatic
        fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx) {
            Log.d(TAG, "Setting up BottomNavigationView")
            bottomNavigationViewEx.enableAnimation(false)
            bottomNavigationViewEx.enableItemShiftingMode(false)
            bottomNavigationViewEx.enableShiftingMode(false)
            bottomNavigationViewEx.setTextVisibility(false)
        }

        @JvmStatic
        fun enableNaviagation(context: Context, view: BottomNavigationViewEx) {
            view.setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.ic_house -> {context.startActivity(Intent(context, HomeActivity::class.java))}
                    R.id.ic_search -> {context.startActivity(Intent(context, SearchActivity::class.java))}
                    R.id.ic_circle -> {context.startActivity(Intent(context, ShareActivity::class.java))}
                    R.id.ic_alert -> {context.startActivity(Intent(context, LikesActivity::class.java))}
                    R.id.ic_android -> {context.startActivity(Intent(context, ProfileActivity::class.java))}

                }
                false
            }
        }
    }
}