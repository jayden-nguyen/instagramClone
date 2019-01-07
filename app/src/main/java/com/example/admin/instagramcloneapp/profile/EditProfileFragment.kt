package com.example.admin.instagramcloneapp.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.utils.UniversalImageLoader
import com.nostra13.universalimageloader.core.ImageLoader
import kotlinx.android.synthetic.main.snippet_center_edit_profile.*
import kotlinx.android.synthetic.main.snippet_top_edit_profile.*

class EditProfileFragment: Fragment() {
    private val TAG = EditProfileFragment::class.simpleName
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setProfileImage()

        backArrow.setOnClickListener {
            activity?.finish()
        }
    }

    private fun setProfileImage() {
        val imgUrl = "https://cdn.vox-cdn.com/thumbor/md1EHFWr0sbMy9Tc0Stf34yKJRQ=/0x0:2040x1360/920x613/filters:focal(857x517:1183x843):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/60742349/wjoel_180413_1777_android_001.0.jpg"
        UniversalImageLoader.setImage(imgUrl,profilePhoto, null)
    }
}