package com.example.admin.instagramcloneapp.utils

class StringManipulation {
    companion object {
        @JvmStatic
        fun expandUserName(userName: String): String {
            return userName.replace(".", " ")
        }

        @JvmStatic
        fun condenseUserName(userName: String): String {
            return userName.replace(" ", ".")
        }
    }
}