package com.example.admin.instagramcloneapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.admin.instagramcloneapp.R
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.assist.ImageScaleType
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener

class UniversalImageLoader (private val mContext: Context){
    companion object {
        val defaultImage = R.drawable.ic_android



        @JvmStatic
        fun setImage(imageUrl: String, image: ImageView, progressBar: ProgressBar?) {
            val imageLoader = ImageLoader.getInstance()
            imageLoader.displayImage( imageUrl, image, object: ImageLoadingListener{
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    if (progressBar != null) {
                        progressBar.visibility = View.GONE
                    }
                }

                override fun onLoadingStarted(imageUri: String?, view: View?) {
                    if (progressBar != null) {
                        progressBar.visibility = View.VISIBLE
                    }
                }

                override fun onLoadingCancelled(imageUri: String?, view: View?) {
                    if (progressBar != null) {
                        progressBar.visibility = View.GONE
                    }
                }

                override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {
                    if (progressBar != null) {
                        progressBar.visibility = View.GONE
                    }
                }
            })
        }
    }

    fun getConfig(): ImageLoaderConfiguration {
        val defaultOptions = DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .cacheOnDisk(true).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(FadeInBitmapDisplayer(300)).build()
        val configuration = ImageLoaderConfiguration.Builder(mContext)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(WeakMemoryCache())
                .diskCacheSize(100 * 1024 * 1024).build()
        return configuration
    }


}