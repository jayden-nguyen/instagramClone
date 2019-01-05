package com.example.admin.instagramcloneapp.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class SectionStatePagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {
    private val mFragmentList = ArrayList<Fragment>()
    private val mFragment = HashMap<Fragment, Int>()
    private val mFragmentNumber = HashMap<String, Int>()
    private val mFragmentNames = HashMap<Int, String>()
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment: Fragment, fragmentName: String) {
        mFragmentList.add(fragment)
        mFragment[fragment] = mFragmentList.size - 1
        mFragmentNumber[fragmentName] = mFragmentList.size - 1
        mFragmentNames[mFragmentList.size - 1] = fragmentName
    }

    /**
     * Return the fragment number with fragment name
     * @param fragmentName
     */
    fun getFragmentNumber(fragmentName: String): Int? {
        if (mFragmentNumber.containsKey(fragmentName)) {
            return mFragmentNumber[fragmentName]
        } else {
            return null
        }
    }

    /**
     * Return the fragment name with fragment number
     * @param fragmentNumber
     */
    fun getFragmentName(fragmentNumber: Int): String? {
        if (mFragmentNames.containsKey(fragmentNumber)) {
            return mFragmentNames[fragmentNumber]
        } else {
            return null
        }
    }

}