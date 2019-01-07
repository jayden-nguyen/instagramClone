package com.example.admin.instagramcloneapp.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

class SquareImageView: ImageView{
    constructor(context: Context): super(context) {}
    constructor(context: Context, attributeSet: AttributeSet): super(context,attributeSet){}
    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int): super(context,attributeSet,defStyle){}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth,measuredWidth)
    }
}