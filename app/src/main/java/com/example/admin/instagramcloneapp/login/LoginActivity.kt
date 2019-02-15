package com.example.admin.instagramcloneapp.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.home.HomeActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity() {

    private val TAG = LoginActivity::class.simpleName
    private lateinit var mAuth: FirebaseAuth
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupFirebaseAuth()
        init()
    }

    private fun init() {
        btnLogin.setOnClickListener {
            Log.d(TAG, "Try to log in")
            if (edtEmail.text.isNullOrEmpty() or edtPassword.text.isNullOrEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                progressBarLogin.visibility = View.VISIBLE
                textWait.visibility = View.VISIBLE
                mAuth.signInWithEmailAndPassword(edtEmail.text.toString(), edtPassword.text.toString()).addOnCompleteListener(this, object : OnCompleteListener<AuthResult>{
                    override fun onComplete(task: Task<AuthResult>) {
                        Log.d(TAG, "signInWithEmail: OnComplete  ${task.isSuccessful}")
                        val user = mAuth.currentUser

                        try {
                            if (user != null) {
                                if (user.isEmailVerified) {
                                    Log.d(TAG, "onComplete: success email is Verified")
                                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                                } else {
                                    Toast.makeText(this@LoginActivity, "Email is not verified \n check your email inbox", Toast.LENGTH_SHORT).show()
                                    progressBarLogin.visibility = View.GONE
                                    textWait.visibility = View.GONE
                                    mAuth.signOut()
                                }

                            }
                        }catch (e: NullPointerException) {
                            Log.e(TAG, "onComplete: NullPointerException ${e.message}")
                        }

                        if (!task.isSuccessful) {
                            Toast.makeText(this@LoginActivity, "Login Failed: ${task.exception}",Toast.LENGTH_SHORT).show()
                            progressBarLogin.visibility = View.GONE
                            textWait.visibility = View.GONE
                            Log.d(TAG, "task exception ${task.exception}")
                        } else {
                            Toast.makeText(this@LoginActivity, "Login Success", Toast.LENGTH_SHORT).show()
                            progressBarLogin.visibility = View.GONE
                            textWait.visibility = View.GONE
                            if (mAuth.currentUser != null) {
                                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            }
                        }
                    }
                })
            }
        }


        linkSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
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