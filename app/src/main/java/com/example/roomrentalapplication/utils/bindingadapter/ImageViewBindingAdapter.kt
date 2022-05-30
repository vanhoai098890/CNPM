package com.example.roomrentalapplication.utils.bindingadapter

import android.content.res.Resources
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.roomrentalapplication.R
import com.example.roomrentalapplication.utils.LogUtils

@BindingAdapter("loadImageUri")
fun ImageView.loadImageUri(uri: String?) {
    if (!uri.isNullOrBlank()) {
//        val radius = resources.getDimensionPixelSize(R.dimen.margin_16dp)
        Glide.with(context).load(Uri.parse(uri))
            .placeholder(R.drawable.bg_luxury_houses)
            .error(R.drawable.bg_luxury_houses).apply(
            RequestOptions().transform(
                CenterCrop()
//                ,
//                GranularRoundedCorners(radius * 1f, radius * 1f, 0f, 0f)
            )
        ).into(this)
    }
}

@BindingAdapter("loadNormalImage")
fun ImageView.loadNormalImage(uri: String?) {
    if (!uri.isNullOrBlank()) {
        Glide.with(context).load(Uri.parse(uri)).placeholder(R.drawable.bg_luxury_houses).into(this)
    }
}

@BindingAdapter("android:src")
fun ImageView.bindImageResource(drawableResId: Int) {
    try {
        setImageResource(drawableResId)
    } catch (e: Resources.NotFoundException) {
        LogUtils.e(e.stackTraceToString())
    }
}
