package com.example.admin.instagramcloneapp.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.utils.FirebaseMethod
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.snippet_center_edit_profile.*

class RegisterActivity: AppCompatActivity() {
    private val TAG = RegisterActivity::class.simpleName
    private lateinit var mAuth: FirebaseAuth
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private lateinit var mFirebaseMethod: FirebaseMethod
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mDatabaseReference: DatabaseReference
    private var append = ""
    private var userName = ""
    private var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setupFirebaseAuth()
        init()
    }

    private fun init() {
        progressBarRegister.visibility = View.GONE
        loadingPleaseWait.visibility = View.GONE
        mFirebaseMethod = FirebaseMethod(this)
        btnRegister.setOnClickListener {
            if (checkInput(edtUsername.text.toString(), edtEmail.text.toString(), edtPassword.text.toString())) {
                progressBarRegister.visibility = View.VISIBLE
                loadingPleaseWait.visibility = View.VISIBLE
                userName = edtUsername.text.toString()
                email = edtEmail.text.toString()
                mFirebaseMethod.registerNewEmail(edtEmail.text.toString(), edtPassword.text.toString(), edtUsername.text.toString())
            }
        }
    }

    private fun checkInput(userName: String, email: String, password: String): Boolean {
        return !(userName.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty())
    }


    //--------------------------------------Firebase --------------------------------------------------
    /**
     * set up Firebase Authenticator
     */
    private fun setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance()
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseDatabase.reference

        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                //User is signed in
                mDatabaseReference.addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {
                        //Make sure user name is not already in use
                        progressBarRegister.visibility = View.GONE
                        loadingPleaseWait.visibility = View.GONE
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        //check user for already existed\
                        progressBarRegister.visibility = View.GONE
                        loadingPleaseWait.visibility = View.GONE
                        if (mFirebaseMethod.checkIfUserNameExisted(edtUsername.text.toString(), dataSnapshot)) {
                            append = mDatabaseReference.push().key!!.substring(3,10)
                            Log.d(TAG, "userName is already exist. Appending random String to name $append")
                        }
                        userName += append
                        //add new User to db
                        mFirebaseMethod.addNewUser(userName, email,"","","")
                        Toast.makeText(this@RegisterActivity, "Signed up Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        mAuth.signOut()
                    }
                })
                finish()
            } else {
                //User is sign out
                Log.d(TAG, "User signed out")
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