package com.example.admin.instagramcloneapp.profile

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_sign_out.*

class SignOutFragment: Fragment() {
    private val TAG = "SignoutFragment"

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mAuthListener: FirebaseAuth.AuthStateListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sign_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.GONE
        tvSigningOut.visibility = View.GONE
        setupFirebaseAuth()
        btnConfirmSignout.setOnClickListener {
            Log.d(TAG,"Onclick attempting to sign out")
            progressBar.visibility = View.VISIBLE
            tvSigningOut.visibility = View.VISIBLE
            mAuth.signOut()

            activity?.finish()
        }
    }
    //--------------------------------------------------------Firebase ---------------------------------------------------------------------
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

                    Log.d(TAG, " Navigate back to Login screen")
                    startActivity(Intent(activity, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    })
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