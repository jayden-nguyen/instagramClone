package com.example.admin.instagramcloneapp.profile

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.admin.instagramcloneapp.R
import com.example.admin.instagramcloneapp.utils.UniversalImageLoader
import kotlinx.android.synthetic.main.item_user_profile_image.view.*

class GridImageAdapter: RecyclerView.Adapter<GridImageAdapter.MyViewHolder>() {
    private var mImageUrlList = ArrayList<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_profile_image, parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mImageUrlList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        UniversalImageLoader.setImage(mImageUrlList[position], holder.itemView.imgItemUserProfile, null)
    }

    fun setImageUrlList(list: ArrayList<String>){
        mImageUrlList = list
        notifyDataSetChanged()
    }

    fun clearImageList() {
        mImageUrlList.clear()
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }
}