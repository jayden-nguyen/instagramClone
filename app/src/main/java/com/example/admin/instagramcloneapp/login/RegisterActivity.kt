package com.example.admin.instagramcloneapp.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.admin.instagramcloneapp.R
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity: AppCompatActivity() {
    private val TAG = RegisterActivity::class.simpleName
    private lateinit var mAuth: FirebaseAuth
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
    //--------------------------------------Firebase --------------------------------------------------
    /**
     * set up Firebase Authenticator
     */
    private fun setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance()
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