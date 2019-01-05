package com.example.admin.instagramcloneapp.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.utils.SectionStatePagerAdapter
import kotlinx.android.synthetic.main.activity_account_setting.*
import kotlinx.android.synthetic.main.layout_center_account_setting.*
import kotlinx.android.synthetic.main.layout_center_viewpager.*
import kotlinx.android.synthetic.main.snippet_top_account_setting.*

class AccountSettingActivity: AppCompatActivity(), SettingAdapter.OnItemClick {

    private val TAG = AccountSettingActivity::class.simpleName

    private val mContext = this

    private val mAdapter = SettingAdapter()

    private lateinit var mPagerAdapter: SectionStatePagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_setting)
        setupSettingList()
        setupFragment()

        backArrow.setOnClickListener {
            finish()
        }
    }

    private fun setupSettingList() {
        val options = ArrayList<String>()

        options.add(getString(R.string.edit_profile))
        options.add(getString(R.string.sign_out))
        rcvAccountSetting.apply {
            mAdapter.setList(options)
            mAdapter.setListener(this@AccountSettingActivity)
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = mAdapter
            addItemDecoration(DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL))
        }
    }

    private fun setupViewPager(fragmentNumber: Int) {
        relLayout1.visibility = View.GONE
        container.adapter = mPagerAdapter
        container.currentItem = fragmentNumber
    }

    private fun setupFragment() {
        mPagerAdapter = SectionStatePagerAdapter(supportFragmentManager)
        mPagerAdapter.addFragment(EditProfileFragment(), getString(R.string.edit_profile))
        mPagerAdapter.addFragment(SignOutFragment(), getString(R.string.sign_out))
    }

    override fun onItemClick(position: Int) {
        setupViewPager(position)
    }
}