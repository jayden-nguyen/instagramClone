package com.example.admin.instagramcloneapp.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.model.User
import com.example.admin.instagramcloneapp.model.UserAccountSetting
import com.example.admin.instagramcloneapp.model.UserSetting
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.NullPointerException

class FirebaseMethod {
    private val TAG = FirebaseMethod::class.simpleName

    private lateinit var mAuth: FirebaseAuth
    private var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private var mContext: Context? = null
    private var userId = ""
    lateinit var mFirebaseDatabase: FirebaseDatabase
    lateinit var mDatabaseReference: DatabaseReference
    constructor(context: Context) {
        mAuth = FirebaseAuth.getInstance()
        mContext = context
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mFirebaseDatabase.reference
        if (mAuth.currentUser != null)  {
            userId = mAuth.currentUser!!.uid
        }
    }

    fun checkIfUserNameExisted(userName: String, dataSnapshot: DataSnapshot): Boolean {
        Log.d(TAG, "CheckIfUserExisted: $userName already exist")
        val user = User()
        for (ds in dataSnapshot.child(userId).children) {
            Log.d(TAG, "CheckIfUserExisted $dataSnapshot")

            user.user_name = ds.getValue(User::class.java)?.user_name
            if (StringManipulation.expandUserName(user.user_name.toString()) == userName) {
                return true
            }
        }

        return false
    }

    fun registerNewEmail(email: String, password: String, userName: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult>{
                    override fun onComplete(task: Task<AuthResult>) {
                        Log.d(TAG, "Create User with Email: onComplete" + task.isSuccessful)
                        if (!task.isSuccessful) {
                            Toast.makeText(mContext, "Failed register ${task.exception}", Toast.LENGTH_SHORT).show()
                        } else if(task.isSuccessful) {
                            sendVerificationEmail()
                            userId = mAuth.currentUser?.uid ?: ""
                            Log.d(TAG, "onComplete: Authstate changed $userId")
                        }
                    }
                })
    }

    fun sendVerificationEmail() {
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(object : OnCompleteListener<Void>{
                override fun onComplete(task: Task<Void>) {
                    if (task.isSuccessful) {

                    } else {
                        Toast.makeText(mContext, "couldn't send verification email ", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    /**
     *
     * Add new user
     * @param userName
     * @param email
     * @param description
     * @param website
     * @param profile_photo
     */
    fun addNewUser(userName: String, email: String, description: String, website: String, profile_photo: String) {
        val user = User(email, 1, userId, StringManipulation.condenseUserName(userName))
        mDatabaseReference.child(mContext?.getString(R.string.dbname_user).toString()).child(userId).setValue(user)
        val userAccountSetting = UserAccountSetting(description, userName, 0,0,0,"",userName, website)
        mDatabaseReference.child(mContext?.getString(R.string.dbname_user_account_setting).toString()).child(userId).setValue(userAccountSetting)
    }

    /**
     * Retrieve data from Firebase
     * @param dataSnapshot
     * @return userAccountSetting
     */
    fun getUserSetting(dataSnapshot: DataSnapshot): UserSetting {
        val setting = UserAccountSetting()
        val user = User()
        for (ds in dataSnapshot.children) {
            if (ds.key!!.equals(mContext?.getString(R.string.dbname_user_account_setting))){
                Log.d(TAG, "Get user account setting dataSnapShot $ds")

                try {
                    setting.display_name = ds.child(userId).getValue(UserAccountSetting::class.java)?.display_name
                    setting.user_name = ds.child(userId).getValue(UserAccountSetting::class.java)?.user_name
                    setting.website = ds.child(userId).getValue(UserAccountSetting::class.java)?.website
                    setting.desscription = ds.child(userId).getValue(UserAccountSetting::class.java)?.desscription
                    setting.followers = ds.child(userId).getValue(UserAccountSetting::class.java)?.followers
                    setting.followings = ds.child(userId).getValue(UserAccountSetting::class.java)?.followings
                    setting.posts = ds.child(userId).getValue(UserAccountSetting::class.java)?.posts
                    setting.profile_photo = ds.child(userId).getValue(UserAccountSetting::class.java)?.profile_photo
                }catch (e: NullPointerException) {
                    Log.e(TAG, "NullPointerException ${e.message}")
                }
            }

            if (ds.key!!.equals(mContext?.getString(R.string.dbname_user))) {
                try {
                    user.user_name = ds.child(userId).getValue(User::class.java)?.user_name
                    user.user_id = ds.child(userId).getValue(User::class.java)?.user_id
                    user.phone_number = ds.child(userId).getValue(User::class.java)?.phone_number
                    user.email = ds.child(userId).getValue(User::class.java)?.email
                }catch (e: NullPointerException) {
                    Log.e(TAG, "NullPointerException ${e.message}")
                }
            }
        }

        return UserSetting(user, setting)
    }
}