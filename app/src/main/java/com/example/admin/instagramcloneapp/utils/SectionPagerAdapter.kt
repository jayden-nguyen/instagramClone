package com.example.admin.instagramcloneapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {
    private val TAG = SectionPagerAdapter::class.simpleName

    private val mFragmentList = ArrayList<Fragment>()
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }

}