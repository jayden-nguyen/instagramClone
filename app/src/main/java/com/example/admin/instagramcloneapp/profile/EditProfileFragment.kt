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
        initImageLoader()
        setProfileImage()

        backArrow.setOnClickListener {
            activity?.finish()
        }
    }

    private fun initImageLoader() {
        val universalImageLoader = UniversalImageLoader(context!!)
        ImageLoader.getInstance().init(universalImageLoader.getConfig())
    }

    private fun setProfileImage() {
        val imgUrl = "https://www.iconspng.com/images/male-avatar/male-avatar.jpg"
        UniversalImageLoader.setImage(imgUrl,profilePhoto, null)
    }
}