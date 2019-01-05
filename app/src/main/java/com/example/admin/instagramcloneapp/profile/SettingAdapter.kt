package com.example.admin.instagramcloneapp.profile

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.instagramcloneapp.R
import kotlinx.android.synthetic.main.item_setting.view.*

class SettingAdapter: RecyclerView.Adapter<SettingAdapter.SettingViewHolder>(){
    private var mSettingList = ArrayList<String>()
    private var mListener: OnItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false)
        return SettingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mSettingList.size
    }

    override fun onBindViewHolder(holder: SettingViewHolder, position: Int) {
        holder.itemView.itemSetting.text = mSettingList[position]
        holder.itemView.setOnClickListener {
            mListener?.onItemClick(position)
        }
    }


    fun setList(list: ArrayList<String>) {
        mSettingList = list
        notifyDataSetChanged()
    }

    inner class SettingViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    fun setListener(listener: OnItemClick) {
        mListener = listener
    }

    interface OnItemClick {
        fun onItemClick(position: Int)
    }

}