package com.example.admin.instagramcloneapp.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Base64DataException
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.login.LoginActivity
import com.example.admin.instagramcloneapp.model.UserSetting
import com.example.admin.instagramcloneapp.utils.BottomNavigationViewHelper
import com.example.admin.instagramcloneapp.utils.FirebaseMethod
import com.example.admin.instagramcloneapp.utils.UniversalImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_bottom_navigation_view.*
import kotlinx.android.synthetic.main.layout_center_profile.*
import kotlinx.android.synthetic.main.snippet_profile_toolbar.*
import kotlinx.android.synthetic.main.snippet_top_profile.*

class ProfileFragment : Fragment() {
    private val TAG = "ProfileFragment"
    private val ACTIVITY_NUM: Int = 4
    private lateinit var mAuth: FirebaseAuth
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mDatabaseRefenrence: DatabaseReference

    private lateinit var mFirebaseMethod: FirebaseMethod

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavigationView()
        setupToolbar()
        setupFirebaseAuth()
        progressBar.visibility = View.GONE
        mFirebaseMethod = context?.let { FirebaseMethod(it) }!!
        profileMenu.setOnClickListener {
            activity?.startActivity(Intent(context, AccountSettingActivity::class.java))
        }

        textEditProfile.setOnClickListener {
            startActivity(Intent(activity, AccountSettingActivity::class.java).apply {
                putExtra(context?.getString(R.string.calling_activity), context?.getString(R.string.profile_activity))
            })
        }
    }

    private fun setupToolbar() {
        (activity as ProfileActivity).setSupportActionBar(profileToolBar)
        profileToolBar.setOnMenuItemClickListener {
            Log.d(TAG, "OnMenuItemClicked: ${it.toString()}")
            when (it.itemId) {
                R.id.profileMenu -> {
                    Log.d(TAG, "OnMenuItemClicked: Navigating ")
                }
            }
            false
        }
    }


    private fun setupBottomNavigationView() {
        Log.d(TAG, "Setup Bottom Navigation View")
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavViewBar)
        context?.let { BottomNavigationViewHelper.enableNaviagation(it, bottomNavViewBar) }
        val menu = bottomNavViewBar.menu
        val menuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.isChecked = true
    }

    private fun setProfileWidget(userSetting: UserSetting) {
        Log.d(TAG, "Setting Widget with data retrieving from firebase database ${userSetting.toString()}")

        val user = userSetting.user
        val userAccountSetting = userSetting.userAccountSetting

        UniversalImageLoader.setImage(userAccountSetting.profile_photo.toString(), profilePhoto, null)
        display_name.text = userAccountSetting.display_name
        profileName.text = userAccountSetting.user_name
        website.text = userAccountSetting.website
        description.text = userAccountSetting.desscription
        tvPosts.text = userAccountSetting.posts.toString()
        tvFollowers.text = userAccountSetting.followers.toString()
        tvFollowing.text = userAccountSetting.followings.toString()
    }

    //--------------------------------------------------------Firebase ---------------------------------------------------------------------
    /**
     * set up Firebase Authenticator
     */
    private fun setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance()
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseRefenrence = mFirebaseDatabase.reference
        mAuthListener = object : FirebaseAuth.AuthStateListener {
            override fun onAuthStateChanged(firebaseAuth: FirebaseAuth) {
                val user = firebaseAuth.currentUser
                if (user != null) {
                    //User is signed in
                    Log.d(TAG, "User is signed in")
                } else {
                    //User is sign out
                    Log.d(TAG, "User signed out")
                }
            }

        }

        mDatabaseRefenrence.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //retrieve data from database
                setProfileWidget(mFirebaseMethod.getUserSetting(dataSnapshot))
            }
        })
    }

    override fun onStart() {
        super.onStart()
        mAuthListener?.let { mAuth.addAuthStateListener(it) }
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener!!)
        }
    }


}