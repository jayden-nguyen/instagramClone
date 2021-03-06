package com.example.admin.instagramcloneapp.home

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.UserHandle
import android.util.Log
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.login.LoginActivity
import com.example.admin.instagramcloneapp.utils.BottomNavigationViewHelper
import com.example.admin.instagramcloneapp.utils.SectionPagerAdapter
import com.example.admin.instagramcloneapp.utils.UniversalImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.layout_bottom_navigation_view.*
import kotlinx.android.synthetic.main.layout_center_viewpager.*
import kotlinx.android.synthetic.main.layout_top_tabs.*

class HomeActivity : AppCompatActivity() {

    private var TAG = HomeActivity::class.simpleName

    private val ACTIVITY_NUM = 0

    //Firebase
    private lateinit var mAuth: FirebaseAuth
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupBottomNavigationView()
        setupViewPager()
        setupFirebaseAuth()
        initImageLoader()
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

    /**
     * Set up Home, Camera and Messages Fragment
     */
    private fun setupViewPager(){
        val adapter = SectionPagerAdapter(supportFragmentManager)
        adapter.addFragment(CameraFragment())
        adapter.addFragment(HomeFragment())
        adapter.addFragment(MessagesFragment())
        container.adapter = adapter

        tabs.setupWithViewPager(container)
    }

    /**
     * Init Image Loader
     */
    private fun initImageLoader() {
        val universalImageLoader = UniversalImageLoader(applicationContext)
        ImageLoader.getInstance().init(universalImageLoader.getConfig())
    }

    //--------------------------------------------------------Firebase ---------------------------------------------------------------------
    private fun checkForCurrentUser(user: FirebaseUser?) {
        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
    /**
     * set up Firebase Authenticator
     */
    private fun setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance()
        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                val user = firebaseAuth.currentUser

                checkForCurrentUser(user)
                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "User is signed in")
                } else {
                    //User is sign out
                    Log.d(TAG, "User signed out")
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        mAuthListener?.let { mAuth.addAuthStateListener(it) }
        checkForCurrentUser(mAuth.currentUser)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener!!)
        }
    }
}
