package com.example.admin.instagramcloneapp.model

data class UserAccountSetting(var desscription: String?= null,
                              var display_name: String?= null,
                              var followers: Long? = null,
                              var followings: Long? = null,
                              var posts: Long?= null,
                              var profile_photo: String?= null,
                              var user_name: String?= null,
                              var website: String?= null
                              )